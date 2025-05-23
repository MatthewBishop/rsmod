package org.rsmod.api.game.process.npc

import jakarta.inject.Inject
import org.rsmod.api.npc.access.StandardNpcAccessLauncher
import org.rsmod.api.npc.events.NpcTimerEvents
import org.rsmod.events.EventBus
import org.rsmod.game.MapClock
import org.rsmod.game.entity.Npc
import org.rsmod.game.timer.NpcTimerMap
import org.rsmod.game.type.npc.UnpackedNpcType

public class NpcTimerProcessor
@Inject
constructor(
    private val mapClock: MapClock,
    private val eventBus: EventBus,
    private val accessLauncher: StandardNpcAccessLauncher,
) {
    public fun process(npc: Npc) {
        if (npc.timerMap.isNotEmpty) {
            npc.processTimers()
        }
    }

    private fun Npc.processTimers() {
        val expired = timerMap.incrementCountersAndGetExpiredKeys()
        for (timerType in expired) {
            publishEvent(timerType.toInt())
        }
    }

    private fun NpcTimerMap.incrementCountersAndGetExpiredKeys(): Set<Short> {
        expiredKeysBuffer.clear()
        for (entry in this) {
            // Note: Counter is incremented _before_ being checked against its interval.
            var counter = extractClockCounter(entry.longValue) + 1
            val interval = extractInterval(entry.longValue)
            if (counter >= interval) {
                expiredKeysBuffer.add(entry.shortKey)
                counter = 0
            }
            val packed = packValues(clockCounter = counter, interval = interval)
            entry.setValue(packed)
        }
        return expiredKeysBuffer
    }

    private fun Npc.publishEvent(timer: Int, type: UnpackedNpcType = visType) {
        val packedType = EventBus.composeLongKey(type.id, timer)
        val typeTrigger = eventBus.suspend[NpcTimerEvents.Type::class.java, packedType]
        if (typeTrigger != null) {
            val event = NpcTimerEvents.Type(this, timer)
            accessLauncher.launch(this) { typeTrigger(event) }
            return
        }

        if (type.contentGroup != -1) {
            val packedContentGroup = EventBus.composeLongKey(type.contentGroup, timer)
            val contentTrigger =
                eventBus.suspend[NpcTimerEvents.Content::class.java, packedContentGroup]
            if (contentTrigger != null) {
                val event = NpcTimerEvents.Content(this, type.contentGroup, timer)
                accessLauncher.launch(this) { contentTrigger(event) }
                return
            }
        }

        val event = NpcTimerEvents.Default(this, timer)
        accessLauncher.launch(this) { eventBus.publish(this, event) }
    }
}
