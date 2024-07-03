<template>
  <v-main width="100%" height="100%" class="pa-0">
    <v-container style="width: 100%; height: 100%; max-width: 100%" class="pa-0 ma-0 ">
      <v-sheet :style="{backgroundImage: backgroundGradient}" width="100%" height="100%">
        <v-row class="justify-center align-center text-center" style="height: 100%">
          <v-col>
            <v-img src="../assets/banner-Pedalise.png" contain alt="Pedalise Banner" height="640"></v-img>
          </v-col>
          <v-col>
            <span style="font-size: 3.0cqh; color: white">
              Um complexo ciclístico em Joinville para proporcionar <br>
              aos ciclistas um sistema compartilhado de serviços e convivência, <br>
              que promova união, praticidade e segurança para as atividades <br>
              do ciclismo e cuidados com as bikes
            </span>
          </v-col>
        </v-row>
      </v-sheet>
    </v-container>
  </v-main>
</template>

<script setup>

const backgroundGradient = 'linear-gradient(0deg, #00a3cb , #00cff7)';

</script>

<style scoped>

</style>
