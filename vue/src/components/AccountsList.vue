<script setup lang="ts">
import { ref, onMounted } from 'vue'

import Account from '@/types/Account'
import AccountsList from '@/types/AccountsList'

import ApiService from '@/services/ApiService'

import { split } from '@/utils'
import AccountOverview from './AccountOverview.vue'

const accounts = ref(null as AccountsList | null)
const accountBlocks = ref([] as (Account[] | null)[])

const retrieve = () => {
  ApiService.getAccounts()
    .then((accountsList: AccountsList) => {
      accounts.value = accountsList

      accountBlocks.value = split(accounts.value?.items ?? [], 3).filter(
        (rowItems) => rowItems !== null
      )
    })
    .catch((e: Error) => {
      console.log(e)
    })
}

onMounted(() => {
  retrieve()
})
</script>

<template>
  <div v-for="(row, index) in accountBlocks" :key="index" class="card-group">
    <AccountOverview
      v-for="(account, index2) in row"
      :key="index2"
      :account="account"
    />
  </div>
</template>
