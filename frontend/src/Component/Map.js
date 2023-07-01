import {
  Box,
  Button,
  ButtonGroup,
  Flex,
  HStack,
  IconButton,
  Input,
  //SkeletonText,
  Text,
} from '@chakra-ui/react'
import { FaLocationArrow, FaTimes } from 'react-icons/fa'
import {useJsApiLoader, GoogleMap,Marker,Autocomplete,DirectionsRenderer,} from '@react-google-maps/api'


import { useState,useRef } from 'react'

const center = {lat:47.17,lng:27.57};

function App() {

  const {isLoaded} = useJsApiLoader({
    googleMapsApiKey: "AIzaSyAkq9vTxvh2ncYMpJozq8QoMkNHyA38vww",
    libraries : ['places'],
  })

  const [, setMap] = useState(/** @type google.maps.Map */ (null))
  const [directionsResponse,setDirectionsResponse] = useState(null)
  const [distance, setDuration] = useState('')
  const [duration,setDistance] = useState('')
  

  /** @type React.MutableRefObject<HTMLInputElement>*/ 
  const OriginRef = useRef()
   /** @type React.MutableRefObject<HTMLInputElement>*/ 
  const DestinationRef = useRef()

  if(!isLoaded){
    //return <SkeletonText/>
  }

  async function calculateRoute(){
    if(OriginRef.current.value === '' || DestinationRef.current.value ===''){
      return
    }
    // eslint-disable-next-line no-undef
    const directionsService = new google.maps.DirectionsService()

    const result = await directionsService.route({
      origin : OriginRef.current.value,
      destination: DestinationRef.current.value,
      // eslint-disable-next-line no-undef
      travelMode: google.maps.TravelMode.TRANSIT

    })

    setDirectionsResponse(result)
    setDistance(result.routes[0].legs[0].distance.text)
    setDuration(result.routes[0].legs[0].duration.text)
  }

  function clearRoute(){
    setDirectionsResponse(null)
    setDuration('')
    setDistance('')

    OriginRef.current.value = ''
    DestinationRef.current.value =''

  }

  return (
    <Flex
      position='relative'
      flexDirection='column'
      alignItems='center'
      bgColor='blue.200'
      // bgImage='https://images.unsplash.com/photo-1647117181799-0ac3e50a548a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80'
      // bgPos='bottom'
      h='100vh'
      w='100vw'
    >
      <Box position='absolute' left={0} top={0} h='90%' w='100%'>
        {/*google maps box*/}
        <GoogleMap 
        center= {center}
         zoom ={16}
          mapContainerStyle={{width:'95%',height:'95%'}}
          options ={{
            zoomControl:true,
            streetViewControl: true,
            mapTypeControl:true,
            fullscreenControl:true
          }}
          onLoad={map => setMap(map)}
          >
            <Marker position = {center}></Marker>
          
          {directionsResponse && <DirectionsRenderer directions = {directionsResponse}/>}  
        
          
        </GoogleMap>
        </Box>
      <Box
        p={4}
        borderRadius='lg'
        mt={4}
        bgColor='white'
        shadow='base'
        minW='container.md'
        zIndex='1'
      >
        <HStack spacing={2} justifyContent='space-between'>
        <Box flexGrow={1}>
          <Autocomplete>
            <Input type='text' placeholder='Origin' ref ={OriginRef} />
          </Autocomplete>
        </Box>
        <Box flexGrow={1}>
          <Autocomplete>
            <Input type='text' placeholder='Destination' ref ={DestinationRef}/>
          </Autocomplete>
        </Box>
          <ButtonGroup>
            <Button colorScheme='pink' type='submit' onClick={calculateRoute}>
              Calculate Route
            </Button>
            <IconButton
              aria-label='center back'
              icon={<FaTimes />}
              onClick={clearRoute}
            />
          </ButtonGroup>
        </HStack>
        <HStack spacing={4} mt={4} justifyContent='space-between'>
          <Text>Distance: {distance} </Text>
          <Text>Duration: {duration}</Text>
          <IconButton
            aria-label='center back'
            icon={<FaLocationArrow />}
            isRound
            onClick={() => {
              //map.panTo(center)
            }}
          />
        </HStack>
      </Box>
    </Flex>
  )
}

export default App