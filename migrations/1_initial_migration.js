const MarketOracle = artifacts.require("MarketOracle");
const Master = artifacts.require("Master");
const GRX = artifacts.require("GRX");

const Factory = artifacts.require("IUniswapV2Factory");
const Pair = artifacts.require("IUniswapV2Pair")

const Router = artifacts.require("IUniswapV2Router02")

module.exports = async function (deployer) {
  //const router = await Router.at('0xD99D1c33F9fC3444f8101754aBC46c52416550D1')
  const factory = await Factory.at('0x6725F303b657a9451d8BA641348b6761A6CC7a17');
  //const factory = await Factory.at('0xBCfCcbde45cE874adCB698cC183deBcF17952812');
  //console.log(factory)
  // const gf = await GRX.at("0x18a7e62750002b0e71cb701eb3cb70d2dca8e7cf");
  // const paddress = await gf.uniswapV2PairAddress();

  // const paddress2 = await Pair.at("0xd2331b41f6Fb1ca8B071770C4693FBE848B73ba4")
  // console.log(await paddress2.token0());
  // console.log(await paddress2.token1());
  // console.log(await paddress2.getReserves());

  

  //const factory = await Factory.at('0x0000000000000000000000000000000000000000');
  //const router = await Router.at('0xD99D1c33F9fC3444f8101754aBC46c52416550D1');

  const pairAddressPFOWBNB = await factory.getPair('0x18a7e62750002b0e71cb701eb3cb70d2dca8e7cf', '0xae13d989daC2f0dEbFf460aC112a837C89BAa7cd')
  const pairAddressETHWBNB = await factory.getPair('0xd66c6b4f0be8ce5b39d52e0fd1344c389929b378', '0xae13d989daC2f0dEbFf460aC112a837C89BAa7cd')
  //const pairAddressBTCBWBNB = await factory.getPair('0x94ad1f196578cdfce4ab88e17be7353d65450350', '0xae13d989daC2f0dEbFf460aC112a837C89BAa7cd')
  //const pairAddressBNBBUSD = await factory.getPair('0x32bcbd0919bb5daa9634201468ce3e8e2fdf5db5', '0xae13d989daC2f0dEbFf460aC112a837C89BAa7cd')
  //const pairAddressBNBUSDT = await factory.getPair('0x567a776acdfe24c99a9588ffc4e663d5165acd2e', '0x4dbf253521e8e8080282c964975f3afb7f87cece')
  //const pairAddressBTCBWBNB = await factory.getPair('0x94ad1f196578cdfce4ab88e17be7353d65450350', '0xae13d989daC2f0dEbFf460aC112a837C89BAa7cd')
  //const pairAddressBTCBBUSD = await factory.getPair('0x94ad1f196578cdfce4ab88e17be7353d65450350', '0x50ba517d64caa5e4fc30b8a010a92fdeed468e9b')
  //const pairAddressBTCBUSDT = await factory.getPair('0x94ad1f196578cdfce4ab88e17be7353d65450350', '0x1413874e27d8d575dac0be8aca645a60826d051f')
  
  //const pairAddressBUSDBNB = await factory.getPair('0xe9e7CEA3DedcA5984780Bafc599bD69ADd087D56', '0xbb4CdB9CBd36B01bD1cBaEBF2De08d9173bc095c')

  console.log(pairAddressPFOWBNB) // 0xd2331b41f6Fb1ca8B071770C4693FBE848B73ba4
  console.log(pairAddressETHWBNB) // 0x4D6F42B822A6Dff31e54f4FC1EEEffD5Ed8830Dd
  //console.log(pairAddressBTCBWBNB) 
  //console.log(pairAddressBNBBUSD)
  //console.log(pairAddressBNBUSDT)
  //console.log(pairAddressBUSDBNB)
  //console.log(pairAddressBTCBWBNB)
  //console.log(pairAddressBNBBUSD)
  //console.log(pairAddressBTCBBUSD)
  //console.log(pairAddressBTCBUSDT)

  //deployer.deploy(MarketOracle, "0xd2331b41f6Fb1ca8B071770C4693FBE848B73ba4", "0x4D6F42B822A6Dff31e54f4FC1EEEffD5Ed8830Dd");
  //deployer.deploy(Master, "0xF7e4f2f89B2e125f0cbA1da00076c37A31b6B085");
  //deployer.deploy(GRX, "0xD99D1c33F9fC3444f8101754aBC46c52416550D1", "0x6e0F26A9bfc81Ac769267387F58ef04bD7d2Bc7a");
};
