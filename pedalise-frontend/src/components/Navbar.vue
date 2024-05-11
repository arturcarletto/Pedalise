<script setup>

</script>

<template>
  <nav class="navbar">
    <RouterLink to="/" class="navbar-logo">
      <img alt="Vue logo" class="logo" src="@/assets/pedalise-logo.png" height="50" />
    </RouterLink>

    <div class="navbar-links">
      <RouterLink to="/">Home</RouterLink>
      <RouterLink to="/agenda">Agenda</RouterLink>
    </div>
  </nav>
</template>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  color: white;
  position: fixed;
  top: 15px;
  left: 0;
  right: 0;
  z-index: 1000;
  height: 80px;
}

.navbar-logo img {
  height: 70px;
}

.navbar-links {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  font-size: 1.5rem;
  width: 100%;
}

a {
  margin: 0 30px;
  text-decoration: none;
  color: #3ac1d7;
  transition: 0.4s;
}

.router-link-active {
  font-weight: bold;
}
</style>