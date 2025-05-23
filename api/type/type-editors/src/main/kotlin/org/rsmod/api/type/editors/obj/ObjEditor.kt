package org.rsmod.api.type.editors.obj

import org.rsmod.api.type.editors.TypeEditor
import org.rsmod.api.type.script.dsl.ObjPluginBuilder
import org.rsmod.game.type.obj.ObjType
import org.rsmod.game.type.obj.UnpackedObjType

public abstract class ObjEditor : TypeEditor<UnpackedObjType>() {
    public fun edit(type: ObjType, init: ObjPluginBuilder.() -> Unit) {
        val type = ObjPluginBuilder(type.internalNameValue).apply(init).build(id = -1)
        cache += type
    }
}
