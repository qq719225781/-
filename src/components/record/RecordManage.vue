<template>
    <div>
        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="请输入包裹名" suffix-icon="el-icon-search" style="width: 200px;"
                      @keyup.enter.native="loadPost"></el-input>
            <el-select v-model="warehouse" placeholder="请选择物流公司" style="margin-left: 8px">
                <el-option
                        v-for="item in warehouseData"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                </el-option>
            </el-select>
            <el-select v-model="pakagetype" placeholder="请选择包裹分类" style="margin-left: 8px">
                <el-option
                        v-for="item in pakagetypeData"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                </el-option>
            </el-select>
            <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
            <el-button type="success" @click="resetParam">重置</el-button>

        </div>
        <el-table :data="tableData"
                  :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
                  border
        >
            <el-table-column prop="id" label="ID" width="60">
            </el-table-column>
            <el-table-column prop="pakagename" label="包裹名" width="100">
            </el-table-column>
            <el-table-column prop="warehousename" label="物流公司" width="100" >
            </el-table-column>
            <el-table-column prop="pakagetypename" label="包裹分类" width="100">
            </el-table-column>
            <el-table-column prop="couriername" label="出库人" width="100">
            </el-table-column>
            <el-table-column prop="username" label="取件人" width="100">
            </el-table-column>
            <el-table-column prop="count" label="取件类型" width="100" :formatter="formatcount">
            </el-table-column>
            <el-table-column prop="createtime" label="操作时间" width="180">
            </el-table-column>
            <el-table-column prop="remark" label="备注" >
            </el-table-column>
            <el-table-column prop="operate" label="操作" v-if="user.roleId!=2">>
                <template slot-scope="scope">
                    <el-popconfirm
                            title="确定删除吗？"
                            @confirm="del(scope.row.id)"
                            style="margin-left: 5px;" >
                        <el-button slot="reference" size="small" type="danger" >删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>

        </el-table>
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[5, 10, 20,30]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
        </el-pagination>


    </div>
</template>

<script>
    export default {
        name: "RecordManage",
        data() {


            return {
                user : JSON.parse(sessionStorage.getItem('CurUser')),
                tableData: [],
                pageSize:10,
                pageNum:1,
                total:0,
                name:'',
                warehouseData:[],
                pakagetypeData:[],
                warehouse:'',
                pakagetype:'',
                centerDialogVisible:false,
                form:{
                    id:'',
                    name:'',
                    warehouse:'',
                    pakagetype:'',
                    count:'',
                    remark:'',
                },

            }
        },
        methods:{

            formatcount(row){
                if(row.count == 1){
                    return "本人"
                }
                if(row.count == 2){
                    return "代取"
                }
            },

            formatWarehouse(row){
                let  temp=this.warehouseData.find(item=>{
                    return item.id==row.warehouse

                })
                return temp && temp.name
            },

            formatPakagetype(row){
                let  temp=this.pakagetypeData.find(item=>{
                    return item.id==row.pakagetype

                })
                return temp && temp.name
            },
            resetForm() {
                this.$refs.form.resetFields();
            },

            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.pageNum=1
                this.pageSize=val
                this.loadPost()
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.pageNum=val
                this.loadPost()
            },

            resetParam(){
                this.name=''
                this.warehouse=''
                this.pakagetype=''

            },

            del(id){
                this.$axios.get(this.$httpUrl+'/record/del?id='+id).then(res=>res.data).then(res=>{
                    console.log(res)
                    if(res.code==200){
                        this.$message({
                            message: '操作成功！',
                            type: 'success'
                        });
                        this.loadPost()
                    }else{
                        this.$message({
                            message: '操作失败！',
                            type: 'error'
                        });
                    }

                })
            },

            loadPost(){
                this.$axios.post(this.$httpUrl+'/record/listPage',{
                    pageSize:this.pageSize,
                    pageNum:this.pageNum,
                    param:{
                        name:this.name,
                        warehouse:this.warehouse+'',
                        pakagetype:this.pakagetype+'',
                        roleId:this.user.roleId+'',
                        userId:this.user.id+''

                    }
                }).then(res=>res.data).then(res=>{
                    console.log(res)
                    if(res.code==200){
                        this.tableData=res.data
                        this.total=res.total
                    }else{
                        alert('获取数据失败')
                    }

                })
            },
            loadWarehouse(){
                this.$axios.get(this.$httpUrl+'/warehouse/list').then(res=>res.data).then(res=>{
                    if(res.code==200){
                        this.warehouseData=res.data
                    }else{
                        alert('获取数据失败')
                    }
                })
            },

            loadPakagetype(){
                this.$axios.get(this.$httpUrl+'/pakagetype/list').then(res=>res.data).then(res=>{
                    if(res.code==200){
                        this.pakagetypeData=res.data
                    }else{
                        alert('获取数据失败')
                    }

                })

            }

        },
        beforeMount() {

            this.loadWarehouse()
            this.loadPakagetype()
            this.loadPost()

        }
    }
</script>

<style scoped>

</style>