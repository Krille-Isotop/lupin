package se.isotop.lupin_moments.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import se.isotop.lupin_moments.R
import kotlin.random.Random

class MessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        var builder = NotificationCompat.Builder(this, getString(R.string.main_notification_channel))
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(Random.nextInt(0, 1000), builder.build())
        }

        super.onMessageReceived(remoteMessage)
    }
}