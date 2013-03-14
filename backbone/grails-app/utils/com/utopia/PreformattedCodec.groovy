package com.utopia

class PreformattedCodec {
    static encode = { String target ->
        return target.replaceAll(/\n/, "<br/>").replaceAll(/  /, "&nbsp;&nbsp;")
    }

    static decode = { String target ->
        return target
    }
}
