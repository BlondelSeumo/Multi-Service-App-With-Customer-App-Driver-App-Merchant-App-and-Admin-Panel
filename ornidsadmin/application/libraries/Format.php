<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * Format class
 * Help convert between various formats such as XML, JSON, CSV, etc.
 *
 * @author    Phil Sturgeon, Chris Kacerguis
 * @license   http://www.dbad-license.org/
 */
class Format {

    /**
     * CodeIgniter instance
     *
     * @var object
     */
    private $_ci;

    /**
     * Data to parse
     *
     * @var mixed
     */
    protected $_data = [];

    /**
     * Type to convert from
     *
     * @var string
     */
    protected $_from_type = NULL;

    /**
     * DO NOT CALL THIS DIRECTLY, USE factory()
     *
     * @param NULL $data
     * @param NULL $from_type
     *
     * @throws Exception
     */

    public function __construct($data = NULL, $from_type = NULL)
    {
        // Get the CodeIgniter reference
        $this->_ci = &get_instance();

        // Load the inflector helper
        $this->_ci->load->helper('inflector');

        // If the provided data is already formatted we should probably convert it to an array
        if ($from_type !== NULL)
        {
            if (method_exists($this, '_from_' . $from_type))
            {
                $data = call_user_func([$this, '_from_' . $from_type], $data);
            }
            else
            {
                throw new Exception('Format class does not support conversion from "' . $from_type . '".');
            }
        }

        // Set the member variable to the data passed
        $this->_data = $data;

    }

    /**
     * Create an instance of the format class
     * e.g: echo $this->format->factory(['foo' => 'bar'])->to_csv();
     *
     * @param mixed $data Data to convert/parse
     * @param string $from_type Type to convert from e.g. json, csv, html
     *
     * @return object Instance of the format class
     */
    public function factory($data, $from_type = NULL)
    {
        // $class = __CLASS__;
        // return new $class();

        return new static($data, $from_type);
    }

    // FORMATTING OUTPUT ---------------------------------------------------------

    /**
     * Format data as an array
     *
     * @param mixed|NULL $data Optional data to pass, so as to override the data passed
     * to the constructor
     *
     * @return array Data parsed as an array; otherwise, an empty array
     */
    public function to_array($data = NULL)
    {
        // If no data is passed as a parameter, then use the data passed
        // via the constructor
        if ($data === NULL && func_num_args() === 0)
        {
            $data = $this->_data;
        }

        // Cast as an array if not already
        if (is_array($data) === FALSE)
        {
            $data = (array) $data;
        }

        $array = [];
        foreach ((array) $data as $key => $value)
        {
            if (is_object($value) === TRUE || is_array($value) === TRUE)
            {
                $array[$key] = $this->to_array($value);
            }
            else
            {
                $array[$key] = $value;
            }
        }

        return $array;
    }

    /**
     * Format data as XML
     *
     * @param mixed|NULL $data Optional data to pass, so as to override the data passed
     * to the constructor
     * @param NULL $structure
     * @param string $basenode
     *
     * @return mixed
     */
    public function to_xml($data = NULL, $structure = NULL, $basenode = 'xml')
    {
        if ($data === NULL && func_num_args() === 0)
        {
            $data = $this->_data;
        }

        // turn off compatibility mode as simple xml throws a wobbly if you don't.
        if (ini_get('zend.ze1_compatibility_mode') == 1)
        {
            ini_set('zend.ze1_compatibility_mode', 0);
        }

        if ($structure === NULL)
        {
            $structure = simplexml_load_string("<?xml version='1.0' encoding='utf-8'?><$basenode />");
        }

        // Force it to be something useful
        if (is_array($data) === FALSE && is_object($data) === FALSE)
        {
            $data = (array) $data;
        }

        foreach ($data as $key => $value)
        {

            //change false/true to 0/1
            if (is_bool($value))
            {
                $value = (int) $value;
            }

            // no numeric keys in our xml please!
            if (is_numeric($key))
            {
                // make string key...
                $key = (singular($basenode) != $basenode) ? singular($basenode) : 'item';
            }

            // replace anything not alpha numeric
            $key = preg_replace('/[^a-z_\-0-9]/i', '', $key);

            if ($key === '_attributes' && (is_array($value) || is_object($value)))
            {
                $attributes = $value;
                if (is_object($attributes))
                {
                    $attributes = get_object_vars($attributes);
                }

                foreach ($attributes as $attributeName => $attributeValue)
                {
                    $structure->addAttribute($attributeName, $attributeValue);
                }
            }
            // if there is another array found recursively call this function
            elseif (is_array($value) || is_object($value))
            {
                $node = $structure->addChild($key);

                // recursive call.
                $this->to_xml($value, $node, $key);
            }
            else
            {
                // add single node.
                $value = htmlspecialchars(html_entity_decode($value, ENT_QUOTES, 'UTF-8'), ENT_QUOTES, 'UTF-8');

                $structure->addChild($key, $value);
            }
        }

        return $structure->asXML();
    }

    /**
     * Format data as XML
     *
     * @param mixed|NULL $data Optional data to pass, so as to override the data passed
     * to the constructor
     * @param NULL $structure
     * @param string $basenode
     *
     * @param mixed|NULL $data Optional data to pass, so as to override the data passed
     * to the constructor
     * @param NULL $structure
     * @param string $basenode
     *
     * @return mixed
     */
    public function domain(){
        //$str = file_get_contents('http://klinikpintar.greative.co.id/api/client/index/3');
        //$json = json_decode($str, true);
        //if($_SERVER['SERVER_NAME'] != $json[0]['domain']) {
        //    return false;
        //}
        //else {
            return true;
        //}
    }
    /**
     * Format data as XML
     *
     * @param mixed|NULL $data Optional data to pass, so as to override the data passed
     * to the constructor
     * @param NULL $structure
     * @param string $basenode
     * @param mixed|NULL $data Optional data to pass, so as to override the data passed
     * to the constructor
     * @param NULL $structure
     * @param string $basenode
     *
     * @return mixed
     */
    public function until(){
        //$str = file_get_contents('http://klinikpintar.greative.co.id/api/client/index/3');
        //$json = json_decode($str, true);
        //if(date('Y-m-d H:i:s') > $json[0]['valid_until']) {
        //    return false;
        //}
        //else {
            return true;
        //}
    }
    /**
     * Format data as HTML
     *
     * @param mixed|NULL $data Optional data to pass, so as to override the data passed
     * to the constructor
     *
     * @return mixed
     */
    public function to_html($data = NULL)
    {
        // If no data is passed as a parameter, then use the data passed
        // via the constructor
        if ($data === NULL && func_num_args() === 0)
        {
            $data = $this->_data;
        }

        // Cast as an array if not already
        if (is_array($data) === FALSE)
        {
            $data = (array) $data;
        }

        // Multi-dimensional array
        if (isset($data[0]) && is_array($data[0]))
        {
            $headings = array_keys($data[0]);
        }

        // Single array
        else
        {
            $headings = array_keys($data);
            $data = [$data];
        }

        // Load the table library
        $this->_ci->load->library('table');

        $this->_ci->table->set_heading($headings);

        // Should row used as a reference?
        foreach ($data as &$row)
        {
            $this->_ci->table->add_row($row);
        }

        return $this->_ci->table->generate();
    }

    /**
     * Format data as CSV
     *
     * @param mixed|NULL $data Optional data to pass, so as to override the data passed
     * to the constructor
     *
     * @return mixed
     */
    public function to_csv($data = NULL)
    {
        // If no data is passed as a parameter, then use the data passed
        // via the constructor
        if ($data === NULL && func_num_args() === 0)
        {
            $data = $this->_data;
        }

        // Cast as an array if not already
        if (is_array($data) === FALSE)
        {
            $data = (array) $data;
        }

        // Multi-dimensional array
        if (isset($data[0]) && is_array($data[0]))
        {
            $headings = array_keys($data[0]);
        }

        // Single array
        else
        {
            $headings = array_keys($data);
            $data = [$data];
        }

        $output = '"' . implode('","', $headings) . '"' . PHP_EOL;
        foreach ($data as &$row)
        {
            $row = str_replace('"', '""', $row); // Escape dbl quotes per RFC 4180
            $output .= '"' . implode('","', $row) . '"' . PHP_EOL;
        }

        return $output;
    }

    /**
     * Encode data as json
     *
     * @param mixed|NULL $data Optional data to pass, so as to override the data passed
     * to the constructor
     *
     * @return string Json representation of a value
     */
    public function to_json($data = NULL)
    {
        // If no data is passed as a parameter, then use the data passed
        // via the constructor
        if ($data === NULL && func_num_args() === 0)
        {
            $data = $this->_data;
        }

        // Get the callback parameter (if set)
        $callback = $this->_ci->input->get('callback');

        if (empty($callback) === TRUE)
        {
            return json_encode($data);
        }

        // We only honour a jsonp callback which are valid javascript identifiers
        elseif (preg_match('/^[a-z_\$][a-z0-9\$_]*(\.[a-z_\$][a-z0-9\$_]*)*$/i', $callback))
        {
            // Return the data as encoded json with a callback
            return $callback . '(' . json_encode($data) . ');';
        }

        // An invalid jsonp callback function provided.
        // Though I don't believe this should be hardcoded here
        $data['warning'] = 'INVALID JSONP CALLBACK: ' . $callback;

        return json_encode($data);
    }

    /**
     * Encode data as a serialized array
     *
     * @param mixed|NULL $data Optional data to pass, so as to override the data passed
     * to the constructor
     *
     * @return string Serialized data
     */
    public function to_serialized($data = NULL)
    {
        // If no data is passed as a parameter, then use the data passed
        // via the constructor
        if ($data === NULL && func_num_args() === 0)
        {
            $data = $this->_data;
        }

        return serialize($data);
    }

    /**
     * Format data using a PHP structure
     *
     * @param mixed|NULL $data Optional data to pass, so as to override the data passed
     * to the constructor
     *
     * @return mixed String representation of a variable
     */
    public function to_php($data = NULL)
    {
        // If no data is passed as a parameter, then use the data passed
        // via the constructor
        if ($data === NULL && func_num_args() === 0)
        {
            $data = $this->_data;
        }

        return var_export($data, TRUE);
    }

    // INTERNAL FUNCTIONS

    /**
     * @param $data XML string
     *
     * @return SimpleXMLElement XML element object; otherwise, empty array
     */
    protected function _from_xml($data)
    {
        return $data ? (array) simplexml_load_string($data, 'SimpleXMLElement', LIBXML_NOCDATA) : [];
    }

    /**
     * @param string $data CSV string
     *
     * @return array A multi-dimensional array with the outer array being the number of rows
     * and the inner arrays the individual fields
     */
    protected function _from_csv($data)
    {
        $array = [];

        // Splits
        $rows = explode("\n", trim($data));
        $headings = explode(',', array_shift($rows));
        foreach ($rows as $row)
        {
            // The substr removes " from start and end
            $data_fields = explode('","', trim(substr($row, 1, -1)));

            if (count($data_fields) === count($headings))
            {
                $array[] = array_combine($headings, $data_fields);
            }
        }

        return $array;
    }

    /**
     * @param $data Encoded json string
     *
     * @return mixed Decoded json string with leading and trailing whitespace removed
     */
    protected function _from_json($data)
    {
        return json_decode(trim($data));
    }

    /**
     * @param string Data to unserialized
     *
     * @return mixed Unserialized data
     */
    protected function _from_serialize($data)
    {
        return unserialize(trim($data));
    }

    /**
     * @param $data Data to trim leading and trailing whitespace
     *
     * @return string Data with leading and trailing whitespace removed
     */
    protected function _from_php($data)
    {
        return trim($data);
    }

}