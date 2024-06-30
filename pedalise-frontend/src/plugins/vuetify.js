// plugins/vuetify.js

// Styles
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'

// Vuetify
import { createVuetify } from 'vuetify'
import * as directives from 'vuetify/directives'
import { VCalendar, VTimePicker } from 'vuetify/labs/components'

export default createVuetify({
  components: {
    VCalendar,
    VTimePicker,
  },
  directives,
  theme: {
    defaultTheme: 'dark',
  },
})
