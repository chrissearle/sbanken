<template>

  <div class="container">
    <div
        v-for="(row, index) in this.accountBlocks"
        :key="index"
        class="card-group"
    >
      <account-overview
          v-for="(account, index2) in row"
          :key="index2"
          :account="account"/>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, PropType} from "vue";
import AccountsList from "@/types/AccountsList";
import AccountOverview from "@/components/AccountOverview.vue";
import {split} from "@/utils";

export default defineComponent({
  name: "accounts",
  components: {AccountOverview},
  props: {
    accounts: Object as PropType<AccountsList>,
  },
  computed: {
    accountBlocks() {
      return split(this.$props.accounts?.items ?? [], 3).filter(
          (rowItems) => rowItems !== null
      );
    },
  },

});
</script>
