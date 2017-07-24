package com.codepath.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flicks.R;
import com.codepath.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by STEPHAN987 on 7/22/2017.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private static class ViewHolderPopular{
        ImageView fullBackdropImage;
    }

    private static class ViewHolderLessPopular{
        TextView title;
        TextView overview;
        ImageView image;
        ImageView backdropImage;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies)
    {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getMoviePopularity().ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return Movie.PopularityValues.values().length;
    }
    // Given the item type, responsible for returning the correct inflated XML layout file
    private View getInflatedLayoutForType(int type) {
        if (type == Movie.PopularityValues.POPULAR.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_popular_movie, null);
        } else if (type == Movie.PopularityValues.LESSPOPULAR.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);
        } else {
            return null;
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context currentContext = getContext();
        Movie movie = getItem(position);

        int type = getItemViewType(position);

        ViewHolderPopular viewHolderPopular = null;
        ViewHolderLessPopular viewHolderLessPopular = null;
        if (convertView == null)
        {

            convertView = getInflatedLayoutForType(type);

            if(type == Movie.PopularityValues.LESSPOPULAR.ordinal()){
                viewHolderLessPopular = new ViewHolderLessPopular();
                viewHolderLessPopular.image = (ImageView) convertView.findViewById(R.id.ivMovieImage);
                viewHolderLessPopular.title = (TextView) convertView.findViewById(R.id.tvTitle);
                viewHolderLessPopular.overview = (TextView) convertView.findViewById(R.id.tvOverview);
                viewHolderLessPopular.backdropImage = (ImageView) convertView.findViewById(R.id.ivBackdropImage);
                convertView.setTag(viewHolderLessPopular);
            }
            else
            {
                viewHolderPopular = new ViewHolderPopular();
                viewHolderPopular.fullBackdropImage = (ImageView) convertView.findViewById(R.id.ivFullBackdropImage);
                convertView.setTag(viewHolderPopular);
            }
        }
        else
        {
            if(type == Movie.PopularityValues.LESSPOPULAR.ordinal()) {
                viewHolderLessPopular = (ViewHolderLessPopular) convertView.getTag();
            }
            else
            {
                viewHolderPopular = (ViewHolderPopular) convertView.getTag();
            }
        }

        if (viewHolderLessPopular != null){
            viewHolderLessPopular.title.setText(movie.getOriginalTitle());
            viewHolderLessPopular.overview.setText(movie.getOverview());
            if(currentContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                Picasso.with(currentContext).load(movie.getPosterPath())
                        .error(R.drawable.no_image_big)
                        .placeholder(R.drawable.loading_placeholder)
                        .transform(new RoundedCornersTransformation(20, 20)).into(viewHolderLessPopular.image);
            }
            else if (currentContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                Picasso.with(currentContext).load(movie.getBackdropPath())
                        .error(R.drawable.no_image_big)
                        .placeholder(R.drawable.loading_placeholder)
                        .transform(new RoundedCornersTransformation(20, 20)).into(viewHolderLessPopular.backdropImage);
            }
        }

        if (viewHolderPopular != null){
            if(currentContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                Picasso.with(currentContext).load(movie.getBackdropPath())
                        .error(R.drawable.no_image)
                        .placeholder(R.drawable.loading_placeholder)
                        .transform(new RoundedCornersTransformation(20, 20)).into(viewHolderPopular.fullBackdropImage);
            }
            else if (currentContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                Picasso.with(currentContext).load(movie.getFullBackdropPath())
                        .error(R.drawable.no_image_big)
                        .placeholder(R.drawable.loading_placeholder)
                        .transform(new RoundedCornersTransformation(20, 20)).into(viewHolderPopular.fullBackdropImage);
            }
        }

        return convertView;
    }


}
