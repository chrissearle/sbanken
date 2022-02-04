<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
      <div class="navbar-nav">
        <router-link to="/" class="nav-link"> Overview </router-link>
        <router-link to="/transfer" class="nav-link"> Transfer </router-link>
      </div>
    </div>
  </nav>

  <div class="container">
    <h1>S'banken</h1>

    <router-view />
  </div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue'

  export default defineComponent({
    name: 'app',
  })
</script>
