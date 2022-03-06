<template>
  <div v-for="(row, index) in accountBlocks()" :key="index" class="card-group">
    <account-overview
      v-for="(account, index2) in row"
      :key="index2"
      :account="account"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AccountsList from "@/types/AccountsList";
import AccountOverview from "@/components/AccountOverview.vue";
import { split } from "@/utils";
import ApiService from "@/services/ApiService";
import ResponseData from "@/types/ResponseData";

export default defineComponent({
  name: "accounts",
  components: { AccountOverview },
  data() {
    return {
      accounts: {} as AccountsList | null,
    };
  },
  methods: {
    accountBlocks() {
      return split(this.accounts?.items ?? [], 3).filter(
        (rowItems) => rowItems !== null
      );
    },
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
});
</script>
