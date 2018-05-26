var HDWalletProvider = require("truffle-hdwallet-provider");

var mnemonic = "fragile emotion security lion expand world gather chunk fish way summer gravity"

module.exports = {
  networks: {
    development: {
      host: "127.0.0.1",
      port: 8545,
      network_id: "*", // Match any network id
    },
    rinkeby: {
      provider: function() {
        return new HDWalletProvider(mnemonic, "https://rinkeby.infura.io/")
      },
      network_id: 3
    }  
  }
};
