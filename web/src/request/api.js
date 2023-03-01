import request from './request'
import WeCom from './WeCom'

export const PostWeComRobot = (params) => WeCom.post('', params)

export const GetTestAPI = (params) => request.get('/test', {params})

export const PostTestAPI = (params) => request.post('/test', params)

export const LoginAPI = (params) => request.post('/login', params)

export const GetAllRoleInfoAPI = (params) => request.get('/usr/role-info', {params})

export const GetIfRoleSeenAPI = (params) => request.get('/orgRole/seen', {params})

export const GetGroupListAPI = (params) => request.get('/group', {params})

export const GetMemberByKlassAPI = (params) => request.get('/klass/'+params.id+'/member-list', {params})

export const GetMemberByOrgAPI = (params, id) => request.get('/org/'+id+'/member-list', {params})

export const GetRoleByOrgAPI = (params, id) => request.get('/org/'+id+'/role-list', {params})

export const DeleteUserAPI = (params) => request.get('/user/delete/'+params.id, {params})

export const PostGrantUserRoleAPI = (params) => request.post('/user/grant', params)

export const GetRoleByUserAPI = (params) => request.get('/role/user', {params})

export const GetNoticeByGroupAPI = (params) => request.get('/notice', {params})

export const GetCurrentRolesAPI = (params) => request.get('/role', {params})

export const PostAddNoticeAPI = (params) => request.post('/notice/add', params)

export const GetRangeKlassAPI = (params) => request.get('/klass', {params})

export const GetCurrentUserAPI = (params) => request.get('/userInfo', {params})

export const PostReplyAPI = (params) => request.post('/reply/add', params)

export const PostSupplyAPI = (params) => request.post('/supply/add', params)

export const PostOwnRoleAPI = (params) => request.post('/role/own', params)

export const PostDeleteUserOrgRoleAPI = (params) => request.post('/userOrgRole/delete', params)

export const PostDeleteAllUserOrgRoleAPI = (params) => request.post('/userOrgRole/deleteAll', params)

export const PostEditRoleAPI = (params) => request.post('/orgRole/edit', params)

export const GetAddRoleAPI = (params) => request.get('/orgRole/add', {params})

export const GetDeleteRoleAPI = (params) => request.get('/orgRole/delete', {params})

export const GetRevokeAPI = (params) => request.get('/user/revoke', {params})

export const GetPersMsgAPI = (params) => request.get('/persMsg', {params})

export const GetConfirmMsgAPI = (params, id) => request.get('/persMsg/'+id, {params})

export const GetAcceptMsgAPI = (params, id) => request.get('/persMsg/'+id+'/accept', {params})

export const GetRejectMsgAPI = (params, id) => request.get('/persMsg/'+id+'/reject', {params})

export const GetUserByNameAPI = (params) => request.get('/user', {params})

export const GetAddInviteMsgAPI = (params, id, username) => request.get('/persMsg/'+id+'/invite/'+username, {params})

export const GetIsSuperRoleAPI = (params) => request.get('/superRole', {params})