import { createRouter, createWebHistory } from 'vue-router'
const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import("@v/Login.vue"),
        meta: {
            login: true
        }
    },
    {
        path: '/notice',
        name: 'Notice',
        component: () => import('@v/Notice.vue')
    },
    {
        path: '/notice1',
        name: 'Notice1',
        component: () => import('@v/Notice-old.vue')
    },
    {
        path: '/mailbox',
        name: 'Mailbox',
        component: () => import('@v/Mailbox.vue')
    },
    {
        path: '/main',
        name: 'Main',
        component: () => import('@v/Main.vue')
    },
    {
        path: '/member1',
        name: 'Member1',
        component: () => import('@v/Member-old.vue')
    },
    {
        path: '/member',
        name: 'Member',
        component: () => import('@v/Member.vue')
    },
    {
        path: '/msg',
        name: 'Msg',
        component: () => import('@v/PersMail.vue')
    },
    {
        path: '/user',
        name: 'User',
        component: () => import('@v/User.vue')
    },
    {
        path: '/role',
        name: 'Role',
        component: () => import('@v/Role.vue')
    },
    //////////////////////////////////////////////////
    {
        path: '/array',
        name: 'ArrayTest',
        component: () => import('@v/home/ArrayTest.vue')
    },
    {
        path: '/test',
        name: 'Test',
        component: () => import('@v/home/Test.vue')
    },
    {
        path: '/dialog',
        name: 'dialog',
        component: () => import('@v/DialogTest.vue')
    }
]
const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const token = sessionStorage.getItem('token')
    console.log('token:', token)
    if (to.name != 'Login' && !token) {
        alert('您还没有登录，请先登录！')
        next({
            name: 'Login'
        })  //没去登录页，还没token，跳转去登录页
    }
    else next()  //正常跳转
})


export default router