<template>
  <div class="container">
    <h4>Transfer</h4>

    <div v-if="from !== null && to !== null">
      <div class="mb-3 row">
        <label for="from" class="col-sm-2 col-form-label">From</label>
        <div class="col-sm-10">
          <select
            class="form-select"
            id="from"
            required
            name="from"
            @change="updateFrom"
          >
            <option
              v-for="(account, fromIndex) in accountOptions"
              :key="fromIndex"
              :value="account.accountId"
            >
              {{ account.accountNumber }}
              -
              {{ account.name }}
              -
              {{ displayAmount(account.available) }}
            </option>
          </select>
        </div>
      </div>

      <div class="mb-3 row">
        <label for="to" class="col-sm-2 col-form-label">To</label>
        <div class="col-sm-10">
          <select
            class="form-select"
            id="to"
            required
            name="to"
            @change="updateTo"
          >
            <option
              v-for="(account, toIndex) in accountOptions"
              :key="toIndex"
              :value="account.accountId"
            >
              {{ account.accountNumber }}
              -
              {{ account.name }}
              -
              {{ displayAmount(account.available) }}
            </option>
          </select>
        </div>
      </div>

      <div class="mb-3 row">
        <label for="year" class="col-sm-2 col-form-label">Amount</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="amount"
            id="amount"
            required
            name="amount"
            type="number"
            step="0.01"
          />
        </div>
      </div>

      <div class="mb-3 row">
        <label for="year" class="col-sm-2 col-form-label">Message</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="message"
            id="message"
            required
            name="message"
            minlength="3"
            maxlength="30"
          />
        </div>
      </div>

      <button
        @click="transfer"
        class="btn btn-success offset-sm-2"
        v-bind:disabled="!validTransfer"
      >
        <span
          v-if="transferring"
          class="spinner-border spinner-border-sm"
          role="status"
          aria-hidden="true"
        />
        Transfer
      </button>
      <div v-if="validTransfer" class="mb-3 offset-sm-2 form-text">
        <p>
          Transfer {{ displayAmount(amount) }} from {{ from?.name }} to
          {{ to?.name }} for {{ message }}
        </p>
      </div>
      <div v-if="!validTransfer" class="mb-3 offset-sm-2 form-text">
        <p>Transfer not valid</p>
      </div>
    </div>

    <div v-if="statusMessage !== ''" class="alert alert-primary" role="alert">
      {{ statusMessage }}
    </div>
    <div v-if="errorMessage !== ''" class="alert alert-warning" role="alert">
      {{ errorMessage }}
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import ApiService from '@/services/ApiService'
import ResponseData from '@/types/ResponseData'
import { amount } from '@/utils'
import Account from '@/types/Account'
import AccountsList from '@/types/AccountsList'

export default defineComponent({
  name: 'transfer-form',
  data() {
    return {
      accounts: { availableItems: 0, items: [] } as AccountsList,
      from: {} as Account | undefined,
      to: {} as Account | undefined,
      amount: 0.0,
      message: '',
      statusMessage: '',
      errorMessage: '',
      transferring: false,
    }
  },
  methods: {
    transfer() {
      this.statusMessage = ''
      this.errorMessage = ''

      if (this.validTransfer) {
        this.transferring = true

        // From and To of undefined would have failed in validTransfer
        ApiService.postTransfer(
          /* eslint-disable @typescript-eslint/no-non-null-assertion */
          this.from!.accountId,
          this.to!.accountId,
          /* eslint-enable @typescript-eslint/no-non-null-assertion */
          this.amount,
          this.message
        )
          .then((response: ResponseData) => {
            if (this.accounts.availableItems > 0) {
              this.from = this.accounts.items[0]
              this.to = this.accounts.items[0]
            } else {
              this.from = undefined
              this.to = undefined
            }

            this.amount = 0
            this.message = ''
            this.statusMessage = response.data
            this.transferring = false
          })
          .catch((e: Error) => {
            this.errorMessage = 'Transfer failed'
            this.transferring = false
            console.log(e)
          })
      }
    },
    displayAmount(val: number): string {
      return amount(val)
    },
    updateFrom(event: Event) {
      this.from = this.findAccount(event)
    },
    updateTo(event: Event) {
      this.to = this.findAccount(event)
    },
    findAccount(event: Event): Account | undefined {
      if (event) {
        let option = event.target as HTMLInputElement

        return (
          this.accounts.items.filter(
            (item) => item.accountId === option.value
          )[0] || null
        )
      }
    },
  },
  computed: {
    validTransfer(): boolean {
      return (
        this.from !== undefined &&
        this.to !== undefined &&
        this.from.accountId !== this.to.accountId &&
        this.amount > 0 &&
        this.message.length >= 3 &&
        this.message.length <= 30 &&
        !this.transferring
      )
    },
    accountOptions(): Account[] {
      return this.accounts.items
    },
  },
  mounted() {
    ApiService.getAccounts()
      .then((response: ResponseData) => {
        this.accounts = response.data

        if (this.accounts.availableItems > 0) {
          this.from = this.accounts.items[0]
          this.to = this.accounts.items[0]
        } else {
          this.from = undefined
          this.to = undefined
        }
      })
      .catch((e: Error) => {
        console.log(e)
      })
  },
})
</script>
