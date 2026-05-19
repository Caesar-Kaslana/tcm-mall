import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const user = ref(null)
  const roles = ref([])
  const permissions = ref([])

  const setUser = (data) => {
    user.value = data.user
    roles.value = data.roles || []
    permissions.value = data.permissions || []
  }

  const logout = () => {
    user.value = null
    roles.value = []
    permissions.value = []
  }

  const hasPermission = (url) => {
    if (!permissions.value || permissions.value.length === 0) return false
    return permissions.value.some(p => p === url || p === '*')
  }

  return { user, roles, permissions, setUser, logout, hasPermission }
})
