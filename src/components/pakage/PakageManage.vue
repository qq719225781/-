<template>
    <div>
        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="请输入包裹编号" suffix-icon="el-icon-search" style="width: 200px;"
                @keyup.enter.native="loadPost"></el-input>
            <el-input v-model="warehouse" placeholder="请输入存放货架" suffix-icon="el-icon-search" style="width: 200px;"
                @keyup.enter.native="loadPost"></el-input>

            <el-select v-model="pakagetype" placeholder="请选择包裹分类" style="margin-left: 8px">
                <el-option v-for="item in pakagetypeData" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
            </el-select>
            <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
            <el-button type="success" style="margin-left: 5px;" @click="resetParam">重置</el-button>
            <el-button type="primary" style="margin-left: 300px;" @click="add" v-if="user.roleId != 2">入库</el-button>
            <el-button type="primary" style="margin-left: 5px;" @click="outPakage" v-if="user.roleId != 2">出库</el-button>

        </div>
        <el-table :data="tableData" :header-cell-style="{ background: '#f2f5fc', color: '#555555' }" border
            highlight-current-row @current-change="selectCurrentChange">
            <el-table-column prop="id" label="ID" width="60">
            </el-table-column>
            <el-table-column prop="name" label="包裹编号" width="120">
            </el-table-column>
            <el-table-column prop="warehouse" label="存放货架" width="120">
            </el-table-column>
            <el-table-column prop="Pakagetype" label="包裹分类" width="120" :formatter="formatPakagetype">
            </el-table-column>
            <el-table-column prop="count" label="取件码" width="120">
            </el-table-column>
            <el-table-column prop="userId" label="取件人" :formatter="formatUserName" width="120">
            </el-table-column>
            <el-table-column prop="logistics" label="物流公司" :formatter="formatWarehouse" width="120">
            </el-table-column>
            <el-table-column prop="remark" label="备注" :formatter="remark">
            </el-table-column>
            <el-table-column prop="operate" label="操作" width="220">
                <template slot-scope="scope">
                    <el-button v-if="user.roleId != 2" size="small" type="success" @click="mod(scope.row)">编辑</el-button>
                    <el-popconfirm v-if="user.roleId != 2" title="确定删除吗？" @confirm="del(scope.row.id)"
                        style="margin-left: 5px;">
                        <el-button slot="reference" size="small" type="danger">删除</el-button>
                    </el-popconfirm>
                    <el-button v-if="user.roleId != 2" size="small" type="info" @click="psong(scope.row)"
                        style="margin-left: 5px;">配送</el-button>
                    <el-button v-if="user.roleId == 2" size="small" type="success"
                        @click="applySend(scope.row)">申请取件</el-button>
                </template>

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




        <el-dialog title="配送物流" :visible.sync="inDialogVisible2" width="30%" center>

            <el-form ref="form1" :rules="rules2" :model="form2" label-width="80px">
                <el-form-item label="配送人" prop="deliveryUserId">
                    <el-select v-model="form2.deliveryUserId" placeholder="请选择物流分类">
                        <el-option v-for="item in kdUser" :key="item.id" :label="item.name" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="收件人" prop="receivingUserId">
                    <el-select v-model="form2.receivingUserId" placeholder="请选择物流分类">
                        <el-option v-for="item in stuUser" :key="item.id" :label="item.name" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="配送时间" prop="count">
                    <el-date-picker v-model="form2.deliveryTime" type="datetime" value-format="timestamp" format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-col :span="20">
                        <el-input type="textarea" v-model="form1.remark"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="inDialogVisible2 = false">取 消</el-button>
                <el-button type="primary" @click="doSendPs">确 定</el-button>
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
            kdUser: [],
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
            inDialogVisible2: false,
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
            form2: {
                id: '',
                deliveryUserId: '',
                deliveryUserName: '',
                receivingUserName: '',
                receivingUserId: '',
                wrapNo: '',
                wrapId: '',
                deliveryTime:'',
                remark:''
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
        psong(row) {
            this.inDialogVisible2 = true;
            this.form2.wrapNo = row.name
            this.form2.wrapId = row.id

        },
        doSelectUser(val) {
            this.tempUser = val
        },
        confirmUser() {
            this.form1.username = this.tempUser.name
            this.form1.userid = this.tempUser.id
            this.innerVisible = false
        },

        formatWarehouse(row) {
            let temp = this.warehouseData.find(item => {
                return item.id == row.logistics

            })
            return temp && temp.name
        },

        formatPakagetype(row) {
            let temp = this.pakagetypeData.find(item => {
                return item.id == row.pakagetype

            })
            return temp && temp.name
        },


        formatUserName(row) {
            let temp = this.stuUser.find(item => {
                return item.id == row.userId

            })
            return temp && temp.name
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
        
        doSendPs() {
            let temp = this.stuUser.find(item => {
                return item.id == this.form2.receivingUserId

            })
            this.form2.receivingUserName = temp.name

            let temp1 = this.kdUser.find(item => {
                return item.id == this.form2.deliveryUserId

            })
            this.form2.deliveryUserName = temp1.name

            alert(this.form2.deliveryTime)
            this.$axios.post(this.$httpUrl + '/delivery/save', this.form2).then(res => res.data).then(res => {
                if (res.code == 200) {
                    this.$message({
                        message: '操作成功！',
                        type: 'success'
                    });
                    this.inDialogVisible2 = false
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

        applySend(row) {
            let param = {
                wrapNo: row.name,


                pickupCode: row.count,

                applyUserId: this.user.id,

                applyUserName: this.user.name,

                pakageId: row.id,

                applyState: "正在申请取件"

            }
            this.$axios.post(this.$httpUrl + '/applyInfo/save', param).then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {

                    this.$message({
                        message: '申请成功！',
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
                debugger
                bao = this.user.id
            }
            this.$axios.post(this.$httpUrl + '/pakage/listPage', {
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
            let param = {
                roleId: 2
            }
            this.$axios.post(this.$httpUrl + '/user/listP', param).then(res => res.data).then(res => {
                if (res.code == 200) {
                    this.stuUser = res.data

                } else {
                    alert('获取数据失败')
                }
            })
        },
        loadDdUser() {
            let param = {
                roleId: 1
            }
            this.$axios.post(this.$httpUrl + '/user/listP', param).then(res => res.data).then(res => {
                if (res.code == 200) {
                    this.kdUser = res.data

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
        this.loadDdUser()
    },


}
</script>

<style scoped></style>