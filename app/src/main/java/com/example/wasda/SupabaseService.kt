import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest



object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://gxygxrmrfqdgptdezbbv.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imd4eWd4cm1yZnFkZ3B0ZGV6YmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTMwMTk2NTgsImV4cCI6MjA2ODU5NTY1OH0.Ol10xToQjqHw4A_DKxirg7hxeEIK-YJBcXSI0hkGxrg"
    ) {
        install(Postgrest)
    }
}
