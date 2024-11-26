/*
Note by Henok:
This hook is used to show a loading spinner for a minimum amount of 3.5 secs.
I made it 3.5 secs intentionally to show the spinner for a longer time so that the user can see the spinner.
*/
import { useState } from 'react';

export const useLoading = () => {
  const [isLoading, setIsLoading] = useState(false);
  const MINIMUM_LOADING_TIME = 3500; // I set the minimum loading time to 3.5 seconds

  const withLoading = async (asyncFunction) => {
    setIsLoading(true);
    const startTime = Date.now();
    
    try {
      const result = await asyncFunction();
      const elapsedTime = Date.now() - startTime;
      if (elapsedTime < MINIMUM_LOADING_TIME) {
        await new Promise(resolve => setTimeout(resolve, MINIMUM_LOADING_TIME - elapsedTime));
      }
      return result;
    } finally {
      setIsLoading(false);
    }
  };
  return { isLoading, withLoading };
};