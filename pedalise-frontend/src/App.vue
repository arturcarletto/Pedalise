<script setup>
import Navbar from './components/Navbar.vue';
</script>

<template>
  <header>
      <div id="app">
        <Navbar />
        <router-view/>
      </div>
  </header>
</template>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }
}
</style>
