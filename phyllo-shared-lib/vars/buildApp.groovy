// shared-library/vars/buildApp.groovy
def call() {
    echo "🏗️ Building application..."
    sh """
        echo 'Installing dependencies...'
        npm install || true
        echo 'Running build...'
        npm run build || echo 'Build simulated (no real app)'
    """
}
