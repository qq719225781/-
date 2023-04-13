<template>
    <div>
        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="请输入包裹编号" suffix-icon="el-icon-search" style="width: 200px;"
                @keyup.enter.native="loadPost"></el-input>

            <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
            <el-button type="success" style="margin-left: 5px;" @click="resetParam">重置</el-button>
        </div>
        <el-table :data="tableData" :header-cell-style="{ background: '#f2f5fc', color: '#555555' }" border
            highlight-current-row @current-change="selectCurrentChange">
            <el-table-column prop="id" label="ID" width="60">
            </el-table-column>
            <el-table-column prop="wrapNo" label="包裹编号" width="180">
            </el-table-column>
            <el-table-column prop="pickupCode" label="取件码" width="180">
            </el-table-column>
            <el-table-column prop="applyUserName" label="申请人">
            </el-table-column>
            <el-table-column prop="applyState" label="申请状态">
            </el-table-column>
            <el-table-column prop="applyTime" label="申请时间">
            </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
            :page-sizes="[5, 10, 20, 30]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>

        <el-dialog title="物流信息" :visible.sync="centerDialogVisible" width="30%" center>

            <el-form ref="form" :rules="rules" :model="form" label-width="80px">

                <el-form-item label="包裹编号" prop="name">
                    <el-col :span="20">
                        <el-input v-model="form.name"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="存放货架" prop="warehouse">
                    <el-col :span="20">
                        <el-input v-model="form.warehouse"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="包裹分类" prop="pakagetype">
                    <el-col :span="20">
                        <el-select v-model="form.pakagetype" placeholder="请选择物流分类">
                            <el-option v-for="item in pakagetypeData" :key="item.id" :label="item.name" :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>

                <el-form-item label="取件用户" prop="pakagetype">
                    <el-col :span="20">
                        <el-select v-model="form.userId" placeholder="请选择物流分类">
                            <el-option v-for="item in stuUser" :key="item.id" :label="item.name" :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>

                <el-form-item label="取件码" prop="count">
                    <el-col :span="20">
                        <el-input v-model="form.count"></el-input>
                    </el-col>
                </el-form-item>

                <el-form-item label="所属物流" prop="pakagetype">
                    <el-col :span="20">
                        <el-select v-model="form.logistics" placeholder="请选择物流分类">
                            <el-option v-for="item in this.warehouseData" :key="item.id" :label="item.name"
                                :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>

                <el-form-item label="备注" prop="remark">
                    <el-col :span="20">
                        <el-input type="textarea" v-model="form.remark"></el-input>
                    </el-col>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="出入库" :visible.sync="inDialogVisible" width="30%" center>

            <el-dialog width="75%" title="用户选择" :visible.sync="innerVisible" append-to-body>
                <SelectUser @doSelectUser="doSelectUser"></SelectUser>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="innerVisible = false">取 消</el-button>
                    <el-button type="primary" @click="confirmUser">确 定</el-button>
                </span>
            </el-dialog>

            <el-form ref="form1" :rules="rules1" :model="form1" label-width="80px">
                <el-form-item label="包裹名">
                    <el-col :span="20">
                        <el-input v-model="form1.pakagename" readonly></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="取件人">
                    <el-col :span="20">
                        <el-input v-model="form1.username" readonly @click.native="selectUser"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="取件类型" prop="count">
                    <el-col :span="20">
                        <el-radio v-model="form1.count" label="1">本人</el-radio>
                        <el-radio v-model="form1.count" label="2">代取</el-radio>
                    </el-col>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-col :span="20">
                        <el-input type="textarea" v-model="form1.remark"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="inDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="doInPakage">确 定</el-button>
            </span>
        </el-dialog>





    </div>
</template>

<script>

import SelectUser from "@/components/user/SelectUser";
export default {
    name: "PakageManage",
    components: { SelectUser },
    data() {

        return {
            user: JSON.parse(sessionStorage.getItem('CurUser')),
            tableData: [],
            pageSize: 10,
            pageNum: 1,
            total: 0,
            name: '',
            warehouseData: [],
            pakagetypeData: [],
            stuUser: [],
            warehouse: '',
            pakagetype: '',
            inDialogVisible: false,
            innerVisible: false,
            centerDialogVisible: false,
            currentRow: {},
            tempUser: {},
            form: {
                id: '',
                name: '',
                warehouse: '',
                pakagetype: '',
                userId: '',
                logistics: '',
                count: '',
                remark: '',
            },
            form1: {
                pakage: '',
                pakagename: '',
                count: '',
                username: '',
                userid: '',
                courierid: '',
                remark: '',
                action: '1'
            },
            rules: {
                name: [
                    { required: true, message: '请输入包裹名', trigger: 'blur' }
                ],
                count: [
                    { required: true, message: '请输入取件码', trigger: 'blur' }
                ],
                warehouse: [
                    { required: true, message: '请选择物流公司', trigger: 'blur' }
                ],

                pakagetype: [
                    { required: true, message: '请选择物流分类', trigger: 'blur' }
                ],
            },
            rules1: {

            },
        }
    },
    methods: {
        doSelectUser(val) {
            this.tempUser = val
        },
        confirmUser() {
            this.form1.username = this.tempUser.name
            this.form1.userid = this.tempUser.id
            this.innerVisible = false
        },
        resetForm() {
            this.$refs.form.resetFields();
        },
        resetInForm() {
            this.$refs.form1.resetFields();
        },

        del(id) {
            this.$axios.get(this.$httpUrl + '/pakage/del?id=' + id).then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {

                    this.$message({
                        message: '操作成功！',
                        type: 'success'
                    });
                    this.loadPost()
                } else {
                    this.$message({
                        message: '操作失败！',
                        type: 'error'
                    });
                }

            })
        },
        mod(row) {
            this.centerDialogVisible = true
            this.$nextTick(() => {
                //赋值到表单
                this.form.id = row.id
                this.form.name = row.name
                this.form.warehouse = row.warehouse
                this.form.pakagetype = row.pakagetype
                this.form.count = row.count
                this.form.remark = row.remark

            })
        },
        add() {

            this.centerDialogVisible = true
            this.$nextTick(() => {
                this.resetForm()
                this.form.id = ''
            })

        },
        inPakage() {
            if (!this.currentRow.id) {
                alert('请选择记录');
                return;
            }
            this.inDialogVisible = true
            this.$nextTick(() => {
                this.resetInForm()
            })

            this.form1.pakagename = this.currentRow.name
            this.form1.pakage = this.currentRow.id
            this.form1.courierid = this.user.id
            this.form1.action = '1'

        },
        outPakage() {
            if (!this.currentRow.id) {
                alert('请选择记录');
                return;
            }
            this.inDialogVisible = true
            this.$nextTick(() => {
                this.resetInForm()
            })

            this.form1.pakagename = this.currentRow.name
            this.form1.pakage = this.currentRow.id
            this.form1.courierid = this.user.id
            this.form1.action = '2'

        },
        selectUser() {
            this.innerVisible = true

        },
        doSave() {
            this.$axios.post(this.$httpUrl + '/pakage/save', this.form).then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {

                    this.$message({
                        message: '操作成功！',
                        type: 'success'
                    });
                    this.centerDialogVisible = false
                    this.loadPost()
                    this.resetForm()
                } else {
                    this.$message({
                        message: '操作失败！',
                        type: 'error'
                    });
                }

            })
        },

        doMod() {
            this.$axios.post(this.$httpUrl + '/pakage/update', this.form).then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {

                    this.$message({
                        message: '操作成功！',
                        type: 'success'
                    });
                    this.centerDialogVisible = false
                    this.loadPost()
                    this.resetForm()
                } else {
                    this.$message({
                        message: '操作失败！',
                        type: 'error'
                    });
                }

            })
        },
        save() {
            this.$refs.form.validate((valid) => {
                if (valid) {
                    if (this.form.id) {
                        this.doMod();
                    } else {
                        this.doSave();
                    }
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });

        },
        doInPakage() {
            console.log(this.form1);
            debugger
            this.$axios.post(this.$httpUrl + '/record/save', this.form1).then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {

                    this.$message({
                        message: '操作成功！',
                        type: 'success'
                    });
                    this.inDialogVisible = false
                    this.loadPost()
                    this.resetInForm()
                } else {
                    this.$message({
                        message: '操作失败！',
                        type: 'error'
                    });
                }

            })
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            this.pageNum = 1
            this.pageSize = val
            this.loadPost()
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.pageNum = val
            this.loadPost()
        },
        selectCurrentChange(val) {
            this.currentRow = val;
        },
        resetParam() {
            this.name = ''
            this.warehouse = ''
            this.pakagetype = ''

        },
        loadPost() {
            let bao = "";
            if (this.user.roleId == 2) {
                bao = this.user.id
            }
            this.$axios.post(this.$httpUrl + '/applyInfo/listPage', {
                pageSize: this.pageSize,
                pageNum: this.pageNum,
                param: {
                    name: this.name,
                    pakagetype: this.pakagetype + '',
                    warehouse: this.warehouse + '',
                    biao: bao,
                }
            }).then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {
                    this.tableData = res.data
                    this.total = res.total
                } else {
                    alert('获取数据失败')
                }

            })
        },

        loadWarehouse() {
            this.$axios.get(this.$httpUrl + '/warehouse/list').then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {
                    this.warehouseData = res.data
                } else {
                    alert('获取数据失败')
                }
            })
        },

        loadPakagetype() {
            this.$axios.get(this.$httpUrl + '/pakagetype/list').then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {
                    this.pakagetypeData = res.data
                } else {
                    alert('获取数据失败')
                }

            })

        },

        loadUser() {
            this.$axios.get(this.$httpUrl + '/user/userList').then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {
                    this.stuUser = res.data
                } else {
                    alert('获取数据失败')
                }

            })
        }

    },
    beforeMount() {
        this.loadWarehouse()
        this.loadPakagetype()
        this.loadPost()
        this.loadUser()

    },


}
</script>

<style scoped></style>