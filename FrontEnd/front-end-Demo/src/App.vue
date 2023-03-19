<script setup>
import {RouterView} from "vue-router"
import navigation from "./components/navigation.vue"
</script>

<template>
  <header>

    <div class="app">
        <navigation/>
        <RouterView/>
    </div>
  </header>

  <main>

  </main>
</template>
