export default {
    async install(Vue, options) {
      Vue.prototype.$isAuthenticated = false;
      Vue.prototype.$userRole = '';
      
      Vue.prototype.$getUserAuthenticate = async function () {
        try {
          let response = await axios.get('/api/getUserAuthenticated', {
            headers: {
              'RequestVerificationToken': 'RequestVerificationToken',
              'X-Requested-With': 'XMLHttpRequest'
            }
          });
          const userData = response.data;
          this.$isAuthenticated = userData.isAuthenticated;
          this.$userRole = userData.roles;
        }
        catch (error) {
          console.error(error);
          this.$isAuthenticated = false;
        }
      }
    }
  }