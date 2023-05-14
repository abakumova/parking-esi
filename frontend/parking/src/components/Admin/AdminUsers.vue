<template>
    <div>
      <div v-for="user in users" :key="user.id">
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
//TODO: in-time user deletion. Bug: user is deleted, but props list is not updated
import UserCard from "@/components/Admin/UserCard/UserCard.vue";
import ApiService from "@/api/ApiService";

export default {
    name: "AdminUsers",
    components: {UserCard},
    props: {
        users: {
            type: Array,
            required: false,
        },
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
    },
    // mounted() {
    //     this.$root.$on("delete-user", (userId) => {
    //         this.users = this.users.filter((user) => user.id !== userId);
    //     });
    // },
}
</script>

<style scoped>
  .admin-user-container {
      border-radius: 8px;
      box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.4);
      padding: 20px;
      margin-bottom: 25px;
  }
</style>