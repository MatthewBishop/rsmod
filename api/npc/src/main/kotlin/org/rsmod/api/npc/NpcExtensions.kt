package org.rsmod.api.npc

import org.rsmod.api.area.checker.AreaChecker
import org.rsmod.api.config.constants
import org.rsmod.api.config.refs.areas
import org.rsmod.api.config.refs.queues
import org.rsmod.api.config.refs.varns
import org.rsmod.api.npc.interact.AiPlayerInteractions
import org.rsmod.game.entity.Npc
import org.rsmod.game.entity.Player
import org.rsmod.game.entity.npc.NpcMode
import org.rsmod.game.interact.InteractionOp

public fun Npc.clearInteractionRoute() {
    clearInteraction()
    abortRoute()
}

public fun Npc.queueDeath() {
    queue(queues.death, 1)
}

public fun Npc.isValidTarget(): Boolean {
    return isSlotAssigned && isVisible && isNotDelayed && hitpoints > 0
}

public fun Npc.isOutOfCombat(): Boolean = !isInCombat()

public fun Npc.isInCombat(): Boolean {
    if (vars[varns.lastattack] + constants.combat_activecombat_delay >= currentMapClock) {
        return true
    }
    return mode == NpcMode.OpPlayer2 || mode == NpcMode.ApPlayer2 || mode == NpcMode.PlayerEscape
}

/** @return `true` if the npc is **currently** in a multi-combat area. */
public fun Npc.mapMultiway(checker: AreaChecker): Boolean {
    return checker.inArea(areas.multiway, coords)
}

public fun Npc.opPlayer2(target: Player, interactions: AiPlayerInteractions) {
    opPlayer(NpcMode.OpPlayer2)
    interactions.interactOp(this, target, InteractionOp.Op2)
}

public fun Npc.apPlayer2(target: Player, interactions: AiPlayerInteractions) {
    opPlayer(NpcMode.ApPlayer2)
    interactions.interactAp(this, target, InteractionOp.Op2)
}

private fun Npc.opPlayer(mode: NpcMode) {
    resetMovement()
    this.mode = mode
}
