# bitslack
simple slack webhook integration for bitbucket server

## Installation
create a slack webhook address within the slack application and add it in place of the address in resources/config.edu 
{:slack-webhook "https://hooks.slack.com/services/my/webhook/here"} 

recommend running as a service on heroku.
### Heroku app
download Heroku CLI
```bash
sudo add-apt-repository "deb https://cli-assets.heroku.com/branches/stable/apt ./"
curl -L https://cli-assets.heroku.com/apt/release.key | sudo apt-key add -
sudo apt-get update
sudo apt-get install heroku
```
clone bitslack
```bash
git clone https://github.com/andrewmcloud/bitslack.git
```
create a heroku app:

*note: when you create an app, a git remote (called heroku) is created and associated with your local git repo*
```bash
heroku create {{app-name-here}}
```
push bitslack to your heroku repo
```bash
git push heroku master
```
ensure an instance is running:
```bash
heroku ps:scale web=1
```
visit your app to ensure it is up and running. You should see a message from bitslack
```bash
heroku open
```
if your bitslack app fails, check the logs for clues
```bash
heroku logs --tail

```
### Local server
enable port forwarding via your local router

start the bitslack server
```bash
lein ring server-headless 3000
```
## Bitbucket Hooks
if using Heroku, add your heroku app URI as a Post-Receive WebHook
```
https://my-heroku-app.herokuapp.com/bitbucket
```
if running locally, add your external IP as a Post-Receive WebHook
```
http://my-external-ip:forwarded-port/bitbucket
```
## Add additional information
modify bitslack.server/app to add additional information to slack message; here is an example JSON response provided by bitbucket 
[Bitbucket Server JSON payload](https://confluence.atlassian.com/bitbucketserver/post-service-webhook-for-bitbucket-server-776640367.html)
## License
do whatever you want with it
