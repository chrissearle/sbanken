<template>
  <div class="container">
    <h1>S'banken</h1>

    <router-view/>
  </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "app",
})
</script>