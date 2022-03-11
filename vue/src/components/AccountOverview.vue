<script setup lang="ts">
import Account from '@/types/Account'

import { amount } from '@/utils'

const props = defineProps<{
  account: Account
}>()
</script>

<template>
  <div v-if="account" class="card m-3" style="width: 300px">
    <div class="card-body">
      <h5 class="card-title">
        {{ props.account.name }}
      </h5>
      <h6 class="card-text">
        {{ amount(props.account.balance) }}
      </h6>
    </div>
    <ul class="list-group list-group-flush">
      <li class="list-group-item">
        Available: {{ amount(props.account.available) }}
      </li>
      <li class="list-group-item">
        Credit: {{ amount(props.account.creditLimit) }}
      </li>
    </ul>
    <div class="card-footer text-end">
      <router-link
        class="btn btn-secondary btn-sm"
        :to="`/detail/${props.account.accountId}`"
      >
        {{ props.account.accountNumber }}
      </router-link>
    </div>
  </div>
</template>
