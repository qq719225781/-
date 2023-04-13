<template>
    <div style="text-align: center;background-color: #f1f1f3;height: 100%;padding: 0px;margin: 0px;">
        <h1 style="font-size: 50px;">{{ '欢迎' + user.name + '登录系统' }}</h1>
        <el-button v-if="user.roleId != 2" size="small" type="success" @click="updatePassword"
            style="margin-left: 1000px;">密码修改</el-button>
        <el-descriptions title="个人中心" :column="2" size="40" border>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-s-custom"></i>
                    账号
                </template>
                {{ user.no }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    电话
                </template>
                {{ user.phone }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-location-outline"></i>
                    性别
                </template>
                <el-tag :type="user.sex === '1' ? 'primary' : 'danger'" disable-transitions><i
                        :class="user.sex == 1 ? 'el-icon-male' : 'el-icon-female'"></i>{{ user.sex == 1 ? "男" : "女" }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-tickets"></i>
                    角色
                </template>
                <el-tag type="success" disable-transitions>{{ user.roleId == 0 ? "超级管理员" : (user.roleId == 1 ? "快递员" : "用户") }}</el-tag>

            </el-descriptions-item>
        </el-descriptions>

        <DateUtils></DateUtils>
        <el-dialog title="密码修改" :visible.sync="inDialogVisible" width="30%" center>

            <el-form ref="form1" :rules="rules" :model="form" label-width="80px">
                <el-form-item label="旧密码">
                    <el-col :span="20">
                        <el-input v-model="form.password" type="password"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="新密码">
                    <el-col :span="20">
                        <el-input v-model="form.newPassword" type="password"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="确认密码" prop="count">
                    <el-col :span="20">
                        <el-input v-model="form.newQrPassword" type="password"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="inDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="send">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import DateUtils from "./DateUtils";
export default {
    name: "Home",
    components: { DateUtils },
    data() {

        return {
            inDialogVisible:false,
            user: {},
            form:{
                password:'',
                newPassword:'',
                newQrPassword:''
            },
            form1:{
                id:'',
                password:''
            },
            rules: {
                password: [
                    { required: true, message: '请输入旧密码', trigger: 'blur' }
                ],
                newPassword: [
                    { required: true, message: '请输入新密码', trigger: 'blur' }
                ],
                newQrPassword: [
                    { required: true, message: '请输入确认密码', trigger: 'blur' }
                ],
            },
        }
    },
    computed: {

    },
    methods: {
        send(){
            debugger
            if(this.user.password != this.form.password){
                this.$message({
                        message: '旧密码输入错误！',
                        type: 'error'
                    });   
                    return;
            }
            if(this.form.newPassword != this.form.newQrPassword){
                this.$message({
                        message: '密码确认输入不一致！',
                        type: 'error'
                    });   
                    return;
            }

            this.form1.password = this.form.newQrPassword
            this.form1.id = this.user.id
            this.$axios.post(this.$httpUrl + '/user/update', this.form1).then(res => res.data).then(res => {
                if (res.code == 200) {
                    this.$message({
                        message: '修改成功！',
                        type: 'success'
                    });
                    this.inDialogVisible2 = false
                    sessionStorage.clear()
                    this.$router.push("/")
                } else {
                    this.$message({
                        message: '操作失败！',
                        type: 'error'
                    });
                }

            })

        },
        updatePassword(){
            this.inDialogVisible = true;
        },
        init() {
            this.user = JSON.parse(sessionStorage.getItem('CurUser'))
        }
    },
    created() {
        this.init()
    }
}
</script>

<style scoped>
.el-descriptions {
    width: 90%;

    margin: 0 auto;
    text-align: center;
}
</style>