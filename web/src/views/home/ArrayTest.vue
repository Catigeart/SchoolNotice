<template>
    <MemberList></MemberList>
</template>

<script>
import MemberList from "@/components/Memberlist.vue"

export default {
    components: {
        MemberList
    },

    data() {
        return {
            nodeArray: [],
            nodeArrayLen: 1
        }
    },
    method: {
        updateNodeNum() {
            this.nodeArray = new Array()
            for (let i = 0; i < this.nodeArrayLen; i++) {
                this.nodeArray[i] = {members:[]}
            }
        },
    }
}

</script>

<style scoped>

</style>