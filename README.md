![](https://github.com/potetee/CryptoCurrency/workflows/fist-try-github-action/badge.svg)
This project is for me training Spring boot.

The function is that this spring boot accept two type of http request.

The first one is get request "/asset" path
    fetch the data the current cash and each crypto currency.

The second one is post request "/deal/{AskOrBid}/{kind}/{amount}" path.
    ask or bid crypto currency.
        {AskOrBid}:the deal type . example - ask ,bid
        {kind}:which currency you want to deal .example - BTC,ETH
        {amount}:how much you want deal . example - 1,100,1000