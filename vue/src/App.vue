<template>
  <div class="container">
    <h1>S'banken</h1>

    <div v-if="!accounts">
      Loading
    </div>

    <accounts v-if="accounts" :accounts="accounts"/>
  </div>
</template>
<script lang="ts">
import Accounts from "@/components/Accounts.vue";
import ApiService from "@/services/ApiService";
import ResponseData from "@/types/ResponseData";
import AccountsList from "@/types/AccountsList";
import {defineComponent} from "vue";

export default defineComponent({
  name: "app",
  components: {Accounts},
  data() {
    return {
      accounts: {} as AccountsList | null
    };
  },
  mounted() {
    ApiService.getAccounts()
        .then((response: ResponseData) => {
          this.accounts = response.data;
        })
        .catch((e: Error) => {
          console.log(e);
        });
  },
})
</script>