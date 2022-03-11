<script setup lang="ts">
import { ref, onMounted } from 'vue'
import ApiService from '@/services/ApiService'
import TransactionList from '@/types/TransactionsList'
import { amount, sumTransactionsByDay, balanceTransactionsByDay } from '@/utils'
import Account from '@/types/Account'
import AccountOverview from './AccountOverview.vue'
import { BarChart, LineChart } from 'vue-chart-3'
import { Chart, TooltipItem, registerables } from 'chart.js'
import { DateTime } from 'luxon'

Chart.register(...registerables)

const props = defineProps<{
  id: string
}>()

const transactions = ref(null as TransactionList | null)
const account = ref(null as Account | null)
const barChartProps = ref({
  chartData: {
    labels: [''],
    datasets: [
      {
        label: 'Amount by day',
        data: [0],
      },
    ],
  },
  options: {
    plugins: {
      tooltip: {
        callbacks: {
          label: function (item: TooltipItem<'bar'>) {
            return amount(item.parsed.y)
          },
        },
      },
    },
  },
})
const balanceChartProps = ref({
  chartData: {
    labels: [''],
    datasets: [
      {
        label: 'Balance',
        data: [0],
      },
    ],
  },
  options: {
    plugins: {
      tooltip: {
        callbacks: {
          label: function (item: TooltipItem<'bar'>) {
            return amount(item.parsed.y)
          },
        },
      },
    },
  },
})

const displayDate = (val: string): string => {
  return DateTime.fromFormat(val, "yyyy-MM-dd'T'hh:mm:ss").toFormat(
    'yyyy-MM-dd'
  )
}

const retrieve = () => {
  ApiService.getAccount(props.id)
    .then((accountData: Account) => {
      account.value = accountData

      ApiService.getTransactions(props.id)
        .then((transactionList: TransactionList) => {
          transactions.value = transactionList

          if (transactions.value?.items) {
            const transactionSum = sumTransactionsByDay(
              transactions.value.items
            )

            const labels = transactionSum.map((tx) => tx.accountingDate)
            const sumAmounts = transactionSum.map((tx) => tx.amount)

            labels.reverse()
            sumAmounts.reverse()

            const balanceSum = balanceTransactionsByDay(
              sumAmounts,
              account.value?.balance || 0
            )

            barChartProps.value.chartData.labels = labels
            barChartProps.value.chartData.datasets[0].data = sumAmounts
            balanceChartProps.value.chartData.labels = labels
            balanceChartProps.value.chartData.datasets[0].data = balanceSum
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

onMounted(() => {
  retrieve()
})
</script>

<template>
  <div class="container" v-if="account">
    <account-overview :account="account" />
  </div>
  <div class="container" v-if="transactions">
    <div class="row">
      <div class="card col">
        <div class="card-body">
          <div class="card-text">
            <bar-chart v-bind="barChartProps" />
          </div>
        </div>
      </div>
      <div class="card col">
        <div class="card-body">
          <div class="card-text">
            <line-chart v-bind="balanceChartProps" />
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
          <td>{{ amount(transaction.amount) }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
