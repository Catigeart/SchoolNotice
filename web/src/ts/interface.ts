export interface GroupRole {
    id: string[],
    name: string[]
}

export interface KlassRole {
    id: string[],
    name: string[]
}

export interface Group {
    id: string,
    type: string,
    name: string
}

export interface Member {
    username: string, 
    name: string,
    sex: string,
    roles: string
}
/////////////////////////model/////////////////////////
export interface AllGroup {
    id: number,
    name: string,
    isKlass: boolean
}

export interface AllRole {
    id: number,
    name: string,
    isKlass: boolean
}

export interface Operation {
    id: number,
    name: string
}

export interface MsgType {
    id: number,
    typeName: string
}

export interface PersMsg {
    id: number,
    name: string,
    msgType: MsgType,
    sendAllGroup: AllGroup,
    sendAllRole: AllRole,
    sendUsername: string,
    receiveAllGroup: AllGroup,
    receiveAllRole: AllRole,
    receiveUsername: string,
    content: string,
    operation: Operation,
    isDone: boolean 
}

/**
 * {
    "msgtype": "text",
    "text": {
        "content": "广州今日天气：29度，大部分多云，降雨概率：60%",
		"mentioned_list":["wangqing","@all"],
		"mentioned_mobile_list":["13800001111","@all"]
    }
 * }
 */
export interface WeCom {
    msgtype: string,
    text: {
        content: string,
        mentioned_list: string[],
        mentioned_mobile_list: string[]
    }
}