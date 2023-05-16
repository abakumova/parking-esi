<template>
    <div>
      <div v-for="user in this.users" :key="user.id">
          <div class="admin-user-container">
              <user-card
                      :user="user"
                      @update-user="updateUser"
                      @delete-user="deleteUser"
              />
          </div>
      </div>
    </div>
</template>

<script>
import UserCard from "@/components/Admin/UserCard/UserCard.vue";
import ApiService from "@/api/ApiService";

export default {
    name: "AdminUsers",
    components: {UserCard},
    data() {
        return {
            users: []
        }
    },
    methods: {
        async deleteUser(userId) {
            await ApiService.user.deleteUser(userId);
            console.log("PARENT DELETE")
            this.users = this.users.filter(user => user.id !== userId);
        },

        async updateUser(updatedUser) {
            await ApiService.user.updateUser(updatedUser.id, updatedUser);
            const index = this.users.findIndex((user) => user.id === updatedUser.id);
            this.$set(this.users, index, updatedUser);
        },
        async fetchUsers() {
            const params = {
                limit:100000,
                page:0,
            }
            const resp = await ApiService.user.getUsers(params)
            this.users = resp.data

            console.warn(`Fetched users:`)
            console.warn(this.users)
        },
    },
    async mounted() {
        await this.fetchUsers()
    },
}
</script>

<style scoped>
  .admin-user-container {
      border-radius: 8px;
      box-shadow: 4px 4px 4px rgba(0, 0, 0, 0.5);
      padding: 20px;
      margin-bottom: 25px;
  }
</style>