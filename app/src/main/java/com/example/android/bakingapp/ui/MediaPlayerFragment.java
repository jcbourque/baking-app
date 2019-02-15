package com.example.android.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class MediaPlayerFragment extends Fragment {

    @BindView(R.id.thumbnail_image_view) ImageView imageView;
    @BindView(R.id.simple_exo_player_view) PlayerView playerView;
    @BindString(R.string.bundle_key_url) String bundleKeyUrl;

    private SimpleExoPlayer simpleExoPlayer;
    private Unbinder unbinder;

    @Setter
    private String videoUrl;
    @Setter
    private String thumbnailUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.media_player_fragment, container, false);
        unbinder = ButterKnife.bind(this, root);
        Context context = getContext();

        if (savedInstanceState != null) {
            videoUrl = savedInstanceState.getString(bundleKeyUrl);
        }

        if (videoUrl == null || videoUrl.isEmpty()) {
            playerView.setVisibility(View.GONE);

            if (thumbnailUrl != null && !thumbnailUrl.isEmpty()) {
                Picasso.get().load(thumbnailUrl).into(imageView);
            }
        } else {
            imageView.setVisibility(View.GONE);

            Uri uri = Uri.parse(videoUrl);

            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context);
            playerView.setPlayer(simpleExoPlayer);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, getString(R.string.app_name)));

            MediaSource video = new ExtractorMediaSource
                    .Factory(dataSourceFactory)
                    .createMediaSource(uri);

            simpleExoPlayer.prepare(video);
            simpleExoPlayer.setPlayWhenReady(true);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(bundleKeyUrl, videoUrl);
    }
}
