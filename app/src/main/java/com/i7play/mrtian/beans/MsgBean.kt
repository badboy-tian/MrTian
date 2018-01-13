package com.i7play.mrtian.beans

/**
 * Created by tian on 2018/1/13.
 */
class MsgBean {
    companion object {
        val LOGINED = 1000
    }

    var from = ""
    var to = ""
    var data:Any ?= null
    var reqNum = 1

    var type = 0

    constructor(type: Int) : super() {
        this.type = type
    }

    constructor(type: Int, from:String) : super() {
        this.type = type
        this.from = from
    }

    constructor(type: Int, from:String, reqNum: Int) : super() {
        this.type = type
        this.from = from
        this.reqNum = reqNum
    }
}