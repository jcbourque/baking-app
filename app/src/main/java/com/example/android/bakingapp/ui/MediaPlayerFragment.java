package com.example.android.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class MediaPlayerFragment extends Fragment {

    @BindView(R.id.simple_exo_player_view) PlayerView playerView;

    private SimpleExoPlayer simpleExoPlayer;
    private Unbinder unbinder;

    @Setter
    private String url;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.media_player_fragment, container, false);
        unbinder = ButterKnife.bind(this, root);
        Context context = getContext();

        if (url == null || url.isEmpty()) {
            Toast.makeText(context, "No Video", Toast.LENGTH_SHORT).show();
            return root;
        }

        Uri uri = Uri.parse(url);

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context);
        playerView.setPlayer(simpleExoPlayer);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, getString(R.string.app_name)));

        MediaSource video = new ExtractorMediaSource
                .Factory(dataSourceFactory)
                .createMediaSource(uri);

        simpleExoPlayer.prepare(video);
        simpleExoPlayer.setPlayWhenReady(true);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
        }

        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
