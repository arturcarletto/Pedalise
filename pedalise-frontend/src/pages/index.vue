<template>
  <v-main width="100%" height="100%" class="pa-0">
    <v-container style="width: 100%; height: 100%; max-width: 100%" class="pa-0 ma-0 ">
      <v-sheet :style="{backgroundImage: backgroundGradient}" width="100%" height="70%">
        <v-img src="../assets/banner-Pedalise.png" contain alt="Pedalise Banner" height="690"></v-img>
      </v-sheet>
      <v-sheet color="#00a3cb" width="100%" height="30%" class="text-center">
        <v-row class="justify-center align-center" style="height: 100%">
          <v-col>
            <span style="font-size: 2.5cqh; color: white">
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
