<template>
  <div class="container" v-if="account">
    <account-overview :account="account" />
  </div>
  <div class="container" v-if="transactions">
    <div class="row">
      <div class="card col">
        <div class="card-body">
          <div class="card-text">
            <bar-chart :chartData="chartData" />
          </div>
        </div>
      </div>
      <div class="card col">
        <div class="card-body">
          <div class="card-text">
            <line-chart :chartData="balanceChartData" />
          </div>
        </div>
      </div>
    </div>

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
  import TransactionSum from '@/types/TransactionsSum'
  import {
    amount,
    sumTransactionsByDay,
    balanceTransactionsByDay,
  } from '@/utils'
  import moment from 'moment'
  import Account from '@/types/Account'
  import AccountOverview from './AccountOverview.vue'
  import { BarChart, LineChart } from 'vue-chart-3'
  import { Chart, registerables } from 'chart.js'

  Chart.register(...registerables)

  export default defineComponent({
    components: { AccountOverview, BarChart, LineChart },
    name: 'account-detail',
    data() {
      return {
        transactions: {} as TransactionList | null,
        account: {} as Account | null,
        chartData: {
          labels: [''],
          datasets: [
            {
              label: 'Amount by day',
              data: [0],
            },
          ],
        },
        balanceChartData: {
          labels: [''],
          datasets: [
            {
              label: 'Balance',
              data: [0],
            },
          ],
        },
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

            ApiService.getTransactions(this.id)
              .then((response: ResponseData) => {
                this.transactions = response.data

                if (this.transactions?.items) {
                  const transactionSum = sumTransactionsByDay(
                    this.transactions.items
                  )

                  const labels = transactionSum.map((tx) => tx.accountingDate)
                  const sumAmounts = transactionSum.map((tx) => tx.amount)

                  labels.reverse()
                  sumAmounts.reverse()

                  const balanceSum = balanceTransactionsByDay(
                    sumAmounts,
                    this.account?.balance || 0
                  )

                  this.$data.chartData.labels = labels
                  this.$data.chartData.datasets[0].data = sumAmounts
                  this.$data.balanceChartData.labels = labels
                  this.$data.balanceChartData.datasets[0].data = balanceSum
                }
              })
              .catch((e: Error) => {
                console.log(e)
              })
          })
          .catch((e: Error) => {
            console.log(e)
          })
      }
    },
  })
</script>
