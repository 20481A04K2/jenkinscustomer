def call(String env) {
    echo "🔍 Validating environment: ${env}"
    if (!["dev", "staging", "prod"].contains(env)) {
        error "❌ Invalid environment: ${env}"
    }
    echo "✅ Pre-check passed for ${env}"
}
