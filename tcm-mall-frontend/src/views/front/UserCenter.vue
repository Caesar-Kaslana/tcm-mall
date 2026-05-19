<template>
  <div class="user-center-page">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card>
          <div class="user-info">
            <el-avatar :size="80" :src="userStore.user?.avatar || 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'" />
            <h3>{{ userStore.user?.nickname || userStore.user?.username }}</h3>
          </div>
          <el-menu :default-active="activeMenu" @select="activeMenu = $event">
            <el-menu-item index="info">个人信息</el-menu-item>
            <el-menu-item index="password">修改密码</el-menu-item>
            <el-menu-item index="address">收货地址</el-menu-item>
          </el-menu>
        </el-card>
      </el-col>
      <el-col :span="18">
        <el-card v-if="activeMenu === 'info'">
          <template #header>个人信息</template>
          <el-form :model="userForm" label-width="100px">
            <el-form-item label="昵称"><el-input v-model="userForm.nickname" /></el-form-item>
            <el-form-item label="手机号"><el-input v-model="userForm.phone" /></el-form-item>
            <el-form-item label="邮箱"><el-input v-model="userForm.email" /></el-form-item>
            <el-form-item><el-button type="primary" @click="saveInfo">保存</el-button></el-form-item>
          </el-form>
        </el-card>

        <el-card v-if="activeMenu === 'password'">
          <template #header>修改密码</template>
          <el-form :model="pwdForm" label-width="100px">
            <el-form-item label="原密码"><el-input v-model="pwdForm.oldPassword" type="password" /></el-form-item>
            <el-form-item label="新密码"><el-input v-model="pwdForm.newPassword" type="password" /></el-form-item>
            <el-form-item label="确认密码"><el-input v-model="pwdForm.confirmPassword" type="password" /></el-form-item>
            <el-form-item><el-button type="primary" @click="savePassword">确认修改</el-button></el-form-item>
          </el-form>
        </el-card>

        <el-card v-if="activeMenu === 'address'">
          <template #header>
            <div style="display: flex; justify-content: space-between; align-items: center;">
              <span>收货地址</span>
              <el-button type="primary" size="small" @click="showAddressDialog = true">新增地址</el-button>
            </div>
          </template>
          <el-card v-for="addr in localAddressList" :key="addr.id" style="margin-bottom: 10px;">
            <div>{{ addr.receiverName }} {{ addr.receiverPhone }}</div>
            <div>{{ addr.province }}{{ addr.city }}{{ addr.district }}{{ addr.detailAddress }}</div>
            <div style="margin-top: 10px;">
              <el-tag v-if="addr.isDefault === 1" type="success" size="small">默认</el-tag>
              <el-button v-else type="text" size="small" @click="setDefault(addr.id)">设为默认</el-button>
              <el-button type="danger" size="small" @click="deleteAddress(addr.id)">删除</el-button>
            </div>
          </el-card>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="showAddressDialog" title="新增地址" width="500px">
      <el-form :model="addressForm" label-width="100px">
        <el-form-item label="收货人"><el-input v-model="addressForm.receiverName" /></el-form-item>
        <el-form-item label="手机号"><el-input v-model="addressForm.receiverPhone" /></el-form-item>
        <el-form-item label="省"><el-input v-model="addressForm.province" /></el-form-item>
        <el-form-item label="市"><el-input v-model="addressForm.city" /></el-form-item>
        <el-form-item label="区"><el-input v-model="addressForm.district" /></el-form-item>
        <el-form-item label="详细地址"><el-input v-model="addressForm.detailAddress" type="textarea" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddressDialog = false">取消</el-button>
        <el-button type="primary" @click="saveAddress">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { useUserStore } from '../../store'
import { getUserInfo, updateUser, updatePassword } from '../../api/user'
import { addressList, addAddress, deleteAddress as apiDeleteAddress, setDefaultAddress } from '../../api/address'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const activeMenu = ref('info')
const userForm = reactive({ nickname: '', phone: '', email: '' })
const pwdForm = reactive({ oldPassword: '', newPassword: '', confirmPassword: '' })
// 本地变量重命名，避免和导入的API函数重名
const localAddressList = ref([])
const showAddressDialog = ref(false)
const addressForm = reactive({ receiverName: '', receiverPhone: '', province: '', city: '', district: '', detailAddress: '' })

const fetchUserInfo = async () => {
  const res = await getUserInfo()
  Object.assign(userForm, res.data)
}

const saveInfo = async () => {
  await updateUser(userForm)
  ElMessage.success('保存成功')
}

const savePassword = async () => {
  if (pwdForm.newPassword !== pwdForm.confirmPassword) {
    ElMessage.error('两次输入密码不一致')
    return
  }
  await updatePassword({ oldPassword: pwdForm.oldPassword, newPassword: pwdForm.newPassword })
  ElMessage.success('修改成功')
}

const fetchAddresses = async () => {
  const res = await addressList()
  localAddressList.value = res.data || []
}

const saveAddress = async () => {
  await addAddress(addressForm)
  ElMessage.success('添加成功')
  showAddressDialog.value = false
  fetchAddresses()
}

const deleteAddress = async (id) => {
  await apiDeleteAddress(id)
  ElMessage.success('删除成功')
  fetchAddresses()
}

const setDefault = async (id) => {
  await setDefaultAddress(id)
  ElMessage.success('设置成功')
  fetchAddresses()
}

onMounted(() => { fetchUserInfo(); fetchAddresses() })
</script>

<style scoped>
.user-center-page { padding: 20px 0; }
.user-info { text-align: center; padding: 20px 0; }
.user-info h3 { margin-top: 10px; }
</style>