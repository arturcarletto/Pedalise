<template>
  <v-app-bar app dense>
    <!-- Logo and home link -->
    <v-toolbar-title>
      <router-link to="/">
        <v-img src="../assets/pedalise-logo.png" contain height="50" alt="Pedalise Logo" class="logo-image"></v-img>
      </router-link>
    </v-toolbar-title>

    <v-spacer></v-spacer>

    <!-- Navigation links -->
    <v-btn text to="/">Home</v-btn>
    <v-btn text to="/agenda">Agenda</v-btn>
  </v-app-bar>
</template>

<script setup>

</script>

<style scoped>
.logo-image {
  margin-left: -30%; /* Adjust this value as needed */
}
</style>
