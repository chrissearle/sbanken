<template>
  <div v-if="account" class="card m-3" style="width: 300px">
    <div class="card-body">
      <h5 class="card-title">
        {{ account.name }}
      </h5>
      <h6 class="card-text">
        {{ displayAmount(account.balance) }}
      </h6>
    </div>
    <ul class="list-group list-group-flush">
      <li class="list-group-item">
        Available: {{ displayAmount(account.available) }}
      </li>
      <li class="list-group-item">
        Credit: {{ displayAmount(account.creditLimit) }}
      </li>
    </ul>
    <div class="card-footer text-end">
      <router-link
        class="btn btn-secondary btn-sm"
        :to="`/detail/${account.accountId}`"
      >
        {{ account.accountNumber }}
      </router-link>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue'
import Account from '@/types/Account'
import { amount } from '@/utils'

export default defineComponent({
  name: 'account-overview',
  methods: {
    displayAmount(val: number): string {
      return amount(val)
    },
  },
  props: {
    account: {
      type: Object as PropType<Account>,
      required: true,
    },
  },
})
</script>
