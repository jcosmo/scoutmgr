package myproject.client.entity;

import com.google.inject.Injector;
import javax.annotation.Nonnull;
import myproject.client.test.util.MyprojectFactorySet;

public class MyprojectFactory
  extends AbstractMyprojectFactory
{
  public MyprojectFactory( @Nonnull final MyprojectFactorySet factorySet,
                           @Nonnull final Injector injector )
  {
    super( factorySet, injector );
  }
}
