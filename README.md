# TestBitcoin
Bitcoin Ticker - My solution with un-resolved issue
I created this app from scratch.
Most of it was working until the adapter was added to the spinner
I have been stuck on this for quite some time and here is the issue:
After the first time retrieve of Bitcoin Average (for BTCUSD) and successful display in the text value, subsequent calls do not seem to enter the onSuccess(int statusCode, Header[] headers...
even though it seems to enter the onStart and also doesn't generate me an error code.
So currently you may or may not see the first Bitcoin Average value displayed in the text field, but choosing another currency you definitely will not see the text field updated.
url seems correct for any currency selected as I am prinintg it in the log/Toast.
