import Vue from 'vue'
import Vuetify from 'vuetify/lib'

Vue.use(Vuetify)

export default new Vuetify({
    theme: {
        light: true,
        themes: {
            light: {
                background: '#DFDBCE',
                primary: '#DFDBCE',
                secondary: '#7FD1AE',
                accent: '#F2954B',
            }
        }
    }
})
