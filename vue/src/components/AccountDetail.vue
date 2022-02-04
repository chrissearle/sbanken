<template>
  <div class="container" v-if="account">
    <account-overview :account="account" />
  </div>
  <div class="container" v-if="transactions">
    <table class="table table-striped">
      <tbody>
        <tr
          v-for="(transaction, index) in transactions.items"
          :class="transaction.amount > 0 ? 'table-primary' : 'table-warning'"
          :key="index"
        >
          <td>{{ displayDate(transaction.accountingDate) }}</td>
          <td>{{ transaction.text }}</td>
          <td>{{ displayAmount(transaction.amount) }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue'
  import ApiService from '@/services/ApiService'
  import ResponseData from '@/types/ResponseData'
  import TransactionList from '@/types/TransactionsList'
  import { amount } from '@/utils'
  import moment from 'moment'
  import Account from '@/types/Account'
  import AccountOverview from './AccountOverview.vue'

  export default defineComponent({
    components: { AccountOverview },
    name: 'account-detail',
    data() {
      return {
        transactions: {} as TransactionList | null,
        account: {} as Account | null,
      }
    },
    props: {
      id: String,
    },
    methods: {
      displayAmount(val: number): string {
        return amount(val)
      },
      displayDate(val: string): string {
        return moment(val).format('DD-MM-YYYY')
      },
    },
    mounted() {
      if (this.id) {
        ApiService.getAccount(this.id)
          .then((response: ResponseData) => {
            this.account = response.data
          })
          .catch((e: Error) => {
            console.log(e)
          })

        ApiService.getTransactions(this.id)
          .then((response: ResponseData) => {
            this.transactions = response.data
          })
          .catch((e: Error) => {
            console.log(e)
          })
      }
    },
  })
</script>
