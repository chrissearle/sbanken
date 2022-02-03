<template>
  <div class="card m-3" style="width: 300px;">
    <div class="card-body">
      <h5 class="card-title">
        {{ this.account.name }}
      </h5>
      <h6 class="card-text">
        {{ amount(this.account.balance) }}
      </h6>
    </div>
    <ul class="list-group list-group-flush">
      <li class="list-group-item">Available: {{ amount(this.account.available) }}</li>
      <li class="list-group-item">Credit: {{ amount(this.account.creditLimit) }}</li>
    </ul>
    <div class="card-footer text-end">
      {{ this.account.accountNumber }}
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, PropType} from "vue";
import Account from "@/types/Account";

export default defineComponent({
  name: "account-overview",
  data() {
    return {
      currencyFormatter: new Intl.NumberFormat("no-NO", {
        style: "currency",
        currency: "NOK",
      }),
    }
  },
  methods: {
    amount(amount: number): string {
      return this.currencyFormatter.format(
          amount
      );
    },
  },
  props: {
    account: Object as PropType<Account>,
  },
});
</script>
