import axios from 'axios'
import PRO from './API_PRO.js'
import Cookies from 'js-cookie'

// let DEV_ENV = true
let APICONFIG
let API = {}

APICONFIG = PRO

for (var api in APICONFIG) {
  API[api] = (function (api) {
    return function (data, context) {
      return new Promise((resolve, reject) => {
        let apiInfo = APICONFIG[api]
        let method = apiInfo.method || APICONFIG.method

        let config = {
          baseURL: APICONFIG.baseURL,
          url: apiInfo.url,
          method: method,
          data: data,
          headers: {
            'Content-Type': 'application/json'
          },
        }

        console.log(config)

        axios(config).then((res) => {
          try {
            let apiData = res.data
            resolve(apiData)
          } catch (err) {
            reject(err);
          }
        }, err => {
          reject(err);
        }).catch( err => {
          reject(err);
        })

      })
    }
  })(api)
}

API.CONFIG = APICONFIG

export default API;