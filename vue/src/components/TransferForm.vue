<script setup lang="ts">
import { ref, onMounted } from 'vue'

import Account from '@/types/Account'
import AccountsList from '@/types/AccountsList'

import ApiService from '@/services/ApiService'

import { amount } from '@/utils'

const accounts = ref({ availableItems: 0, items: [] } as AccountsList)
const from = ref(undefined as Account | undefined)
const to = ref(undefined as Account | undefined)
const transferAmount = ref(0.0)
const message = ref('')
const statusMessage = ref('')
const errorMessage = ref('')
const transferring = ref(false)

const transfer = () => {
  statusMessage.value = ''
  errorMessage.value = ''

  if (validTransfer()) {
    transferring.value = true

    // From and To of undefined would have failed in validTransfer
    ApiService.postTransfer(
      /* eslint-disable @typescript-eslint/no-non-null-assertion */
      from.value!.accountId,
      to.value!.accountId,
      /* eslint-enable @typescript-eslint/no-non-null-assertion */
      transferAmount.value,
      message.value
    )
      .then((status: string) => {
        if (accounts.value.availableItems > 0) {
          from.value = accounts.value.items[0]
          to.value = accounts.value.items[0]
        } else {
          from.value = undefined
          to.value = undefined
        }

        transferAmount.value = 0
        message.value = ''
        statusMessage.value = status
        transferring.value = false
      })
      .catch((e: Error) => {
        errorMessage.value = 'Transfer failed'
        transferring.value = false
        console.log(e)
      })
  }
}

const updateFrom = (event: Event) => {
  from.value = findAccount(event)
}

const updateTo = (event: Event) => {
  to.value = findAccount(event)
}

const findAccount = (event: Event): Account | undefined => {
  if (event) {
    let option = event.target as HTMLInputElement

    return (
      accounts.value.items.filter(
        (item) => item.accountId === option.value
      )[0] || null
    )
  }
}

const validTransfer = (): boolean =>
  from.value !== undefined &&
  to.value !== undefined &&
  from.value.accountId !== to.value.accountId &&
  transferAmount.value > 0 &&
  message.value.length >= 3 &&
  message.value.length <= 30 &&
  !transferring.value

const accountOptions = (): Account[] => accounts.value.items

const retrieve = () => {
  ApiService.getAccounts()
    .then((accountsList: AccountsList) => {
      accounts.value = accountsList

      if (accounts.value.availableItems > 0) {
        from.value = accounts.value.items[0]
        to.value = accounts.value.items[0]
      } else {
        from.value = undefined
        to.value = undefined
      }
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
              v-for="(account, fromIndex) in accountOptions()"
              :key="fromIndex"
              :value="account.accountId"
            >
              {{ account.accountNumber }}
              -
              {{ account.name }}
              -
              {{ amount(account.available) }}
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
              v-for="(account, toIndex) in accountOptions()"
              :key="toIndex"
              :value="account.accountId"
            >
              {{ account.accountNumber }}
              -
              {{ account.name }}
              -
              {{ amount(account.available) }}
            </option>
          </select>
        </div>
      </div>

      <div class="mb-3 row">
        <label for="year" class="col-sm-2 col-form-label">Amount</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="transferAmount"
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
        v-bind:disabled="!validTransfer()"
      >
        <span
          v-if="transferring"
          class="spinner-border spinner-border-sm"
          role="status"
          aria-hidden="true"
        />
        Transfer
      </button>
      <div v-if="validTransfer()" class="mb-3 offset-sm-2 form-text">
        <p>
          Transfer {{ amount(transferAmount) }} from {{ from?.name }} to
          {{ to?.name }} for {{ message }}
        </p>
      </div>
      <div v-if="!validTransfer()" class="mb-3 offset-sm-2 form-text">
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
